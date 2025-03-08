package com.fawry.user_api.service;

import com.fawry.user_api.dto.AuthenticationResponse;
import com.fawry.user_api.dto.LogInRequest;
import com.fawry.user_api.dto.SignUpRequest;
import com.fawry.user_api.dto.UserResponse;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.repository.UserRepository;

public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Boolean signUp(SignUpRequest request) {


        ///todo: we must encode password
        User user = new User
                (request.username(),request.email(),request.password(),request.role());


        if (userRepository.existsByUsername(user.getUsername())) {
           throw new EntityNotFoundException("this username already exists");
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public AuthenticationResponse logIn(LogInRequest request) {

        //todo: depends on JWT
        return null;
    }
}
