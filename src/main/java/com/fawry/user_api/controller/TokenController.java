package com.fawry.user_api.controller;

import com.fawry.user_api.dto.user.UserClaimsDTO;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.mapper.UserClaimsMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/token")
public class TokenController {

    private final UserClaimsMapper userClaimsMapper;


    @GetMapping("/admin/validation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserClaimsDTO> validateAdminToken() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userClaimsMapper.getClaims(user));
    }


    @GetMapping("/delivery/validation")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<UserClaimsDTO> validateDeliveryToken() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userClaimsMapper.getClaims(user));
    }
    @GetMapping("/customer/validation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<UserClaimsDTO> validateCustomerToken() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userClaimsMapper.getClaims(user));
    }

    @GetMapping("/user/validation")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserClaimsDTO> validateUserToken() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userClaimsMapper.getClaims(user));
    }
}