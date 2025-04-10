package com.fawry.user_api.controller;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.user.EmailRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;
import com.fawry.user_api.service.AuthenticationService;
import com.fawry.user_api.service.PasswordResetService;
import com.fawry.user_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@Valid @RequestBody AuthenticationRequest logInRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(logInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> singUp(@Valid @RequestBody RegisterRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.register(signUpRequest));
    }
    @PutMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws NoSuchAlgorithmException {
        Map<String, String> response = new HashMap<>();
        passwordResetService.resetPassword(passwordResetRequest);
        response.put("message", "Password reset successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/reset-password-request")
    public ResponseEntity<String> resetPasswordRequest
            (@Valid @RequestBody EmailRequest emailRequest) throws NoSuchAlgorithmException {
        passwordResetService.passwordResetRequest(emailRequest.email());
        return
                new ResponseEntity<>("check your email, a request has been sent to you ", HttpStatus.OK);
    }

}
