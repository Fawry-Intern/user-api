package com.fawry.user_api.dto.auth;


import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest
        (
                @NotBlank(message = "Username is required")
                @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
                String username,

                @Email(message = "Invalid email format")
                @NotNull
                String email,

                @NotBlank(message = "Password is required")
                String password,

                UserRole role
        ) {
}
