package com.fawry.user_api.service;

import com.fawry.user_api.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }
}
