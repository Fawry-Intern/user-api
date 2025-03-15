package com.fawry.user_api.dto.user;

import jakarta.validation.constraints.NotNull;

public record PasswordChangeRequest(
        @NotNull Long userId,
        @NotNull  String oldPassword,
        @NotNull  String newPassword
) {
}
