package com.fawry.user_api.mapper;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {
    private final PasswordEncoder passwordEncoder;

    public AuthenticationMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse toAuthResponse(String token, Long userId)
    {
        return new AuthenticationResponse(token,userId);
    }
    public User mapFromSignRequestToUser(RegisterRequest request) {
        return User.builder().
                username(request.username()).
                email(request.email()).
                password(request.password()).
                role(request.role()).
                isActive(true).
                build();
    }
}
