package com.fawry.user_api.dto;

import jakarta.validation.constraints.NotBlank;

public record LogInRequest
        (
                @NotBlank(message = "email mustn't be blank")
                String email,
                @NotBlank(message = "password mustn't be blank")
                String password
        )
{
}
