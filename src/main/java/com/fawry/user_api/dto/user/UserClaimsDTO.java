package com.fawry.user_api.dto.user;

public record UserClaimsDTO
        (
                String UserId,
                String Email,
                String  Role

        ) {
}
