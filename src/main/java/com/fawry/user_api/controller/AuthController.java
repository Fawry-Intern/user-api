package com.fawry.user_api.controller;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.service.AuthenticationService;
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
    public ResponseEntity<AuthenticationResponse>login(@Valid @RequestBody AuthenticationRequest logInRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(logInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> singUp(@Valid @RequestBody RegisterRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.register(signUpRequest));
    }
}
