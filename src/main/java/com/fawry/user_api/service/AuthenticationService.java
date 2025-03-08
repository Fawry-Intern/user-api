package com.fawry.user_api.service;

import com.fawry.user_api.dto.AuthenticationResponse;
import com.fawry.user_api.dto.LogInRequest;
import com.fawry.user_api.dto.SignUpRequest;
import com.fawry.user_api.dto.UserResponse;

public interface AuthenticationService {
    Boolean signUp(SignUpRequest request);

    AuthenticationResponse logIn(LogInRequest request);

}
