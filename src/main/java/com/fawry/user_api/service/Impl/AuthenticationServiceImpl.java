package com.fawry.user_api.service.Impl;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.exception.DuplicateResourceException;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.exception.IllegalActionException;
import com.fawry.user_api.exception.ResourceType;
import com.fawry.user_api.mapper.AuthenticationMapper;
import com.fawry.user_api.repository.UserRepository;
import com.fawry.user_api.security.JwtService;
import com.fawry.user_api.service.AuthenticationService;
import com.fawry.user_api.util.PasswordValidationHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthenticationMapper authenticationMapper;

    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, AuthenticationMapper authenticationMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authenticationMapper = authenticationMapper;
    }


    @Override
    public Boolean register(RegisterRequest request) {

        if (!PasswordValidationHelper.isValid(request.password())) {
            throw new IllegalActionException("Password does not meet security requirements");
        }
        User user = authenticationMapper.toUserEntity(request);

        if (userRepository.existsByUsername(user.getUsername())) {
           throw new DuplicateResourceException("this username already exists", ResourceType.USERNAME);
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!PasswordValidationHelper.isValid(request.password())) {
            throw new IllegalActionException("Password does not meet security requirements");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        User user=userRepository.findByEmail(request.email()).orElseThrow(()->new EntityNotFoundException("user not found"));
        String token=jwtService.generateToken(user);

        return authenticationMapper.toAuthResponse(token, user.getId());
    }
}
