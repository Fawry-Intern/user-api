package com.fawry.user_api.mapper;

import com.fawry.user_api.dto.user.UserClaimsDTO;
import com.fawry.user_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserClaimsMapper {
    public  UserClaimsDTO getClaims(User user)
    {
        return new UserClaimsDTO(String.valueOf(user.getId()),user.getEmail(),String.valueOf(user.getRole()));

    }
}
