package com.fawry.user_api.mapper;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {
    private final PasswordEncoder passwordEncoder;

    public AuthenticationMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse toAuthResponse(String token, Long userId,UserRole role)
    {
        return new AuthenticationResponse(token,userId,role);
    }
    public User mapFromSignRequestToCustomer(RegisterRequest request) {
        return User.builder().
                username(request.userName()).
                email(request.email()).
                password(passwordEncoder.encode(request.password())).
                role(UserRole.valueOf("CUSTOMER")).
                isActive(true).
                build();
    }

    public User mapFromSignRequestToDelivery(RegisterRequest request) {
        return User.builder().
                username(request.userName()).
                email(request.email()).
                password(passwordEncoder.encode(request.password())).
                role(UserRole.valueOf("DELIVERY")).
                isActive(true).
                build();
    }
}
