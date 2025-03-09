package com.fawry.user_api.service;

import com.fawry.user_api.dto.PasswordChangeRequest;
import com.fawry.user_api.dto.UserResponse;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<UserResponse> findAllUsers() {
        return  userRepository.findAll().stream().map(UserResponse::of).toList();
    }

    @Override
    public UserResponse getUserProfile(Long userId) {
        User user=getUserEntity(userId);
    return new UserResponse(userId, user.getUsername(),user.getEmail(),user.getIsActive(),user.getRole());
    }


    @Transactional
    @Override
    public Boolean removeAccount(Long userId) {

        User user=getUserEntity(userId);
        userRepository.delete(user);

        return true;
    }

    @Transactional
    @Override
    public UserResponse activateUser(Long userId) {
        User user=getUserEntity(userId);
            user.setIsActive(true);
            userRepository.save(user);
        return  new UserResponse(userId, user.getUsername(),user.getEmail(),user.getIsActive(),user.getRole());
    }

    @Transactional
    @Override
    public UserResponse deactivateUser(Long userId) {
        User user=getUserEntity(userId);
        user.setIsActive(false);
        userRepository.save(user);
        return  new UserResponse(userId, user.getUsername(),user.getEmail(),user.getIsActive(),user.getRole());
    }

    @Transactional
    @Override
    public Long changeUserPassword( PasswordChangeRequest passwordChangeRequest) {
          User user =getUserEntity(passwordChangeRequest.userId());

    String encodedPassword=passwordEncoder.encode(passwordChangeRequest.oldPassword());

          if(!user.getPassword().equals(encodedPassword))
              throw new ValidationException("two passwords aren't identical");
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return user.getId();
    }

    private User getUserEntity(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User with id= "+userId+" doesn't exist"));
    }

}
