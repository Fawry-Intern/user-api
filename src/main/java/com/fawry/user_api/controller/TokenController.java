package com.fawry.user_api.controller;

import com.fawry.user_api.dto.user.UserClaimsDTO;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.mapper.UserClaimsMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/token")
public class TokenController {

    private final UserClaimsMapper userClaimsMapper;

    public TokenController(UserClaimsMapper userClaimsMapper) {
        this.userClaimsMapper = userClaimsMapper;
    }

    //just for admin responsibilities
    @GetMapping("/admin/validation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserClaimsDTO> validateAdminToken() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userClaimsMapper.getClaims(user));
    }

    //here roles must be both client and admin
    @GetMapping("/user/validation")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<UserClaimsDTO> validateUserToken() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userClaimsMapper.getClaims(user));
    }

}