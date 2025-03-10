package com.fawry.user_api.controller;

import com.fawry.user_api.dto.AuthenticationResponse;
import com.fawry.user_api.dto.LogInRequest;
import com.fawry.user_api.dto.SignUpRequest;
import com.fawry.user_api.service.AuthenticationService;
import com.fawry.user_api.service.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@Valid @RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(authenticationService.logIn(logInRequest));
    }
    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> singUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }
}
