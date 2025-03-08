package com.fawry.user_api.controller;

import com.fawry.user_api.dto.AuthenticationResponse;
import com.fawry.user_api.dto.PasswordChangeRequest;
import com.fawry.user_api.dto.UserResponse;
import com.fawry.user_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable Long userId)
    {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> removeAccount(@PathVariable Long userId)
    {

      return   ResponseEntity.ok(userService.removeAccount(userId));

    }

    @PutMapping("/change-password")
    public ResponseEntity<Long>changeUserAccountPassword(@RequestBody PasswordChangeRequest passwordChangeRequest)
    {
     return ResponseEntity.ok(userService.changeUserPassword(passwordChangeRequest));
    }

    //admin authorities only
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/activate/{userId}")
    public ResponseEntity<UserResponse> activateUser(@PathVariable Long userId)
    {
        return ResponseEntity.ok(userService.activateUser(userId));
    }
    @PostMapping("/deactivate/{userId}")
    public ResponseEntity<UserResponse> deactivateUser(@PathVariable Long userId)
    {
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }



}
