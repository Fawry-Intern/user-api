package com.fawry.user_api.dto;

public record PasswordChangeWrapper
        (PasswordChangeRequest passwordChangeRequest,
         UserDetailsDTO userDetails
        ) {
}
