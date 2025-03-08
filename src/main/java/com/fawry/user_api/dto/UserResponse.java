package com.fawry.user_api.dto;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserResponse
        ( @NotNull Long id,
         @NotNull  String username,
         @NotNull String email,
         boolean isActive,
         UserRole role
        )
{
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getIsActive(),
                user.getRole()
        );
    }
}
