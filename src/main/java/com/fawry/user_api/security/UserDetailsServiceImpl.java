package com.fawry.user_api.security;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.repository.UserRepository;
import com.fawry.user_api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String userEmail) {

        return  userRepository.findByEmail(userEmail)
                .orElseThrow(()->new EntityNotFoundException("user with email "+userEmail+" doesn't exist"));
    }
}
