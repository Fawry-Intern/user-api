package com.fawry.user_api.dto;

import java.util.List;
import java.util.Set;

public record UserDetailsDTO(
     String username,
     String email,
    String role,
     boolean isActive,
     Set<String> authorities
) {
}
