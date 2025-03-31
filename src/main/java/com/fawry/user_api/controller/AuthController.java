package com.fawry.user_api.controller;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;
import com.fawry.user_api.service.AuthenticationService;
import com.fawry.user_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@Valid @RequestBody AuthenticationRequest logInRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(logInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> singUp(@Valid @RequestBody RegisterRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.register(signUpRequest));
    }

}
