package com.fawry.user_api.service;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;

public interface AuthenticationService {
    Boolean register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
