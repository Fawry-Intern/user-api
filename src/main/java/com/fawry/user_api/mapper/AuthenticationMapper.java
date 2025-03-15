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
    public User toUserEntity(RegisterRequest request)
    {
        return new User(
                request.username(),
                request.email(),
               passwordEncoder.encode( request.password()),
                request.role()
        );
    }
}
