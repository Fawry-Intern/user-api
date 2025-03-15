package com.fawry.user_api.dto.user;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserDetailsResponse
        (  Long id,
           String username,
          String email,
         boolean isActive,
         UserRole role
        )
{
    public static UserDetailsResponse of(User user) {
        return new UserDetailsResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getIsActive(),
                user.getRole()
        );
    }
}
