package com.fawry.user_api.service;

import com.fawry.user_api.entity.User;

public interface UserDetailsService {
    User loadUserByUsername(String username);
}
