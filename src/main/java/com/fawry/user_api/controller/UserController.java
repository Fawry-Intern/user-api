package com.fawry.user_api.controller;

import com.fawry.user_api.dto.*;
import com.fawry.user_api.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@Valid @PathVariable Long userId, @RequestBody UserDetailsDTO userDetails)
    {
        return ResponseEntity.ok(userService.getUserProfile(userId,userDetails));
    }

    @PutMapping("/change-password")
    public ResponseEntity<Long>changeUserAccountPassword
            (@RequestBody PasswordChangeWrapper passwordChangeWrapper)
    {
     return ResponseEntity.ok(
             userService.changeUserAccountPassword(passwordChangeWrapper.passwordChangeRequest()
             ,passwordChangeWrapper.userDetails()));
    }
     @PutMapping("/reset-password")
     public ResponseEntity<Long> resetUserAccountPassword
             (@RequestBody PasswordResetWrapper passwordResetWrapper)
     {
      return ResponseEntity.ok(
              userService.resetUserAccountPassword(passwordResetWrapper.passwordResetRequest()
              ,passwordResetWrapper.userDetails()));
     }

    //admin authorities only
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<UserResponse> activateUser(@Valid @PathVariable Long userId,@RequestBody UserDetailsDTO userDetails)
    {
        return ResponseEntity.ok(userService.activateUser(userId,userDetails));
    }
    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<UserResponse> deactivateUser
            (@Valid @PathVariable Long userId
            ,@RequestBody UserDetailsDTO userDetails)
    {
        return ResponseEntity.ok(userService.deactivateUser(userId,userDetails));
    }



}
