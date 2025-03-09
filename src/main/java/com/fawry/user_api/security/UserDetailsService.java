package com.fawry.user_api.security;

import com.fawry.user_api.entity.User;

public interface UserDetailsService {
    User loadUserByUsername(String username);
}
