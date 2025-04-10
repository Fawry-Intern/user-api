package com.fawry.user_api.mapper;

import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.delivery_person.DeliveryPersonCreationDetails;
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

    public AuthenticationResponse toAuthResponse(String token, Long userId,UserRole role,String email)
    {
        return  AuthenticationResponse.builder().accessToken(token).userId(userId).role(role).email(email).build();
    }
    public User mapFromSignRequestToCustomer(RegisterRequest request) {
        return User.builder().
                firstName(request.firstName()).
                lastName(request.lastName()).
                email(request.email()).
                password(passwordEncoder.encode(request.password())).
                role(UserRole.valueOf("CUSTOMER")).
                isActive(true).
                build();
    }

    public User mapFromSignRequestToDelivery(DeliveryPersonCreationDetails request) {
        return User.builder().
                firstName(request.firstName()).
                lastName(request.lastName()).
                email(request.email()).
                password(passwordEncoder.encode(request.password())).
                role(UserRole.valueOf("DELIVERY")).
                isActive(true).
                build();
    }
}
