package com.fawry.user_api.dto.user;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDetailsResponse
        (  Long id,
           String firstName,
          String lastName,
          String email,
         Boolean isActive,
         UserRole role
        )
{

}
