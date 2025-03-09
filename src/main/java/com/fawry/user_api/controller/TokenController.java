package com.fawry.user_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class TokenController {
    //just for admin responsibilities
    @GetMapping("/admin/validation")
    public ResponseEntity<Void> validateAdminToken() {
        return ResponseEntity.ok().build();
    }

    //here roles must be both client and admin
    @GetMapping("/user/validation")
    public ResponseEntity<Void> validateUserToken() {
        return ResponseEntity.ok().build();
    }

}