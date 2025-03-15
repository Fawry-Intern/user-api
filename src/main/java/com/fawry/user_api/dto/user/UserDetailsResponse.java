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

}
