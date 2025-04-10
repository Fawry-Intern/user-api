package com.fawry.user_api.dto.auth;


import com.fawry.user_api.annotations.ValidPassword;
import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequest
        (
                @NotBlank(message = "Username is required")
                @Size(min = 3, max = 255, message = "First name must be between 3 and 255 characters")
                String firstName,

                @NotBlank(message = "Username is required")
                @Size(min = 3, max = 255, message = "First name must be between 3 and 255 characters")
                String lastName,

                @Email(message = "Invalid email format")
                @NotBlank
                String email,

                @NotBlank(message = "Password is required")
                @Size(min = 8, message = "Password must be at least 8 characters long")
                @ValidPassword
                String password

        ) {
}
