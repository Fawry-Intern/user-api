package com.fawry.user_api.dto;

public record PasswordResetWrapper
        (PasswordResetRequest passwordResetRequest
        ,UserDetailsDTO userDetails
        ) {
}
