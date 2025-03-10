package com.fawry.user_api.controller;

import com.fawry.user_api.util.UserClaimsHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/token")
public class TokenController {


    //just for admin responsibilities
    @GetMapping("/admin/validation")
    public ResponseEntity<UserDetails> validateAdminToken() {
        UserDetails userDetails= UserClaimsHelper.getClaims();

        return ResponseEntity.ok(userDetails);
    }

    //here roles must be both client and admin
    @GetMapping("/user/validation")
    public ResponseEntity<UserDetails> validateUserToken() {

        UserDetails userDetails= UserClaimsHelper.getClaims();
        return ResponseEntity.ok(userDetails);
    }

}