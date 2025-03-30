package com.fawry.user_api.mapper;

import com.fawry.user_api.dto.user.UserDetailsResponse;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserDetailsResponse toUserResponse(User user) {
        return
                UserDetailsResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .isActive(user.getIsActive())
                        .build();
    }


}