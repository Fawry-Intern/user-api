package com.fawry.user_api.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationResponse
        (
              @NotNull String accessToken,
              @NotNull Long userId
        ) {
}
