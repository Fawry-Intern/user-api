package com.fawry.user_api.dto.user;
import com.fawry.user_api.annotations.ValidPassword;
import jakarta.validation.constraints.NotNull;

public record PasswordResetRequest
        (
                @NotNull
                String token,

                @NotNull
                @ValidPassword
                String newPassword
        )
{
}
