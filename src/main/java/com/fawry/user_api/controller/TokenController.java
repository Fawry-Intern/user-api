package com.fawry.user_api.controller;

import com.fawry.user_api.util.UserClaimsHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/token")
public class TokenController {


    //just for admin responsibilities
    @GetMapping("/admin/validation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> validateAdminToken() {
        Long userId= UserClaimsHelper.getClaims();

        return ResponseEntity.ok(userId);
    }

    //here roles must be both client and admin
    @GetMapping("/user/validation")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<Long> validateUserToken() {

        Long userId= UserClaimsHelper.getClaims();
        return ResponseEntity.ok(userId);
    }

}