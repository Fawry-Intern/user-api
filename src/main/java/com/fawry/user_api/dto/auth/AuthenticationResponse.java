package com.fawry.user_api.dto.auth;

import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record AuthenticationResponse
        (
               String accessToken,
               Long userId,
               UserRole role
        ) {
}
