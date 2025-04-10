package com.fawry.user_api.service.Impl;


import com.fawry.kafka.events.ResetPasswordEvent;
import com.fawry.kafka.producer.ResetPasswordProducer;
import com.fawry.user_api.dto.user.PasswordChangeRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;
import com.fawry.user_api.entity.PasswordChangeRequests;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.repository.PasswordChangeRequestsRepository;
import com.fawry.user_api.repository.UserRepository;
import com.fawry.user_api.service.PasswordResetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordChangeRequestsRepository passwordChangeRequestsRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResetPasswordProducer producer;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${frontend.port}")
    private int frontendPort;

    public PasswordResetServiceImpl(UserRepository userRepository, PasswordChangeRequestsRepository passwordChangeRequestsRepository, PasswordEncoder passwordEncoder, ResetPasswordProducer producer) {
        this.userRepository = userRepository;
        this.passwordChangeRequestsRepository = passwordChangeRequestsRepository;
        this.passwordEncoder = passwordEncoder;
        this.producer = producer;

    }

    @Override
    public Boolean passwordResetRequest(String email) throws NoSuchAlgorithmException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        String token = generateAndStoreToken(user);

        sendPasswordResetEvent(user, token);

        return true;
    }

    private String generateAndStoreToken(User user) throws NoSuchAlgorithmException {
        String token = UUID.randomUUID().toString();

        String hashedToken = hashToken(token);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plus(5, ChronoUnit.MINUTES);
        savePasswordChangeRequest(hashedToken, expirationTime, user);

        return token;
    }

    private String hashToken(String token) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(token.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    private void savePasswordChangeRequest(String hashedToken, LocalDateTime expirationTime, User user) {
        passwordChangeRequestsRepository.save(new PasswordChangeRequests(hashedToken, expirationTime, user));
    }

    private void sendPasswordResetEvent(User user, String token) {
        String resetLink = createNewResetLink(token);

        System.out.println(resetLink);
       var even = createNewInstance(user, resetLink);

       producer.produceResetPasswordEvent(even);
    }

    @Transactional
    public Boolean resetPassword(PasswordResetRequest passwordResetRequest) throws NoSuchAlgorithmException {

            String hashedToken = hashToken(passwordResetRequest.token());
            Optional<PasswordChangeRequests> request = passwordChangeRequestsRepository.findByToken(hashedToken);
            if (request.isEmpty()) {
                throw new EntityNotFoundException("Password reset request not found");
            }

            if (request.get().isExpired()) {
                throw new IllegalStateException("Token has expired");
            }

            User user = request.get().getUser();

            user.setPassword(passwordEncoder.encode(passwordResetRequest.newPassword()));

            return true;

    }

    private ResetPasswordEvent createNewInstance(User user, String resetLink) {
        return new ResetPasswordEvent(user.getUsername(), user.getEmail(), resetLink);
    }

    private String createNewResetLink(String token) {
        return String.format("%s:%d/reset-password?token=%s", frontendUrl, frontendPort, token);
    }

}
