package com.fawry.user_api.mapper;

import com.fawry.user_api.dto.user.UserDetailsResponse;
import com.fawry.user_api.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserDetailsResponse toUserResponse(User user) {
        return new UserDetailsResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getIsActive(),
                user.getRole()
        );
    }


}