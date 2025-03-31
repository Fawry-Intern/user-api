package com.fawry.user_api.controller;

import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.delivery_person.DeliveryPersonCreationDetails;
import com.fawry.user_api.dto.user.PasswordChangeRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;
import com.fawry.user_api.dto.user.UserDetailsResponse;
import com.fawry.user_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserProfile( @PathVariable Long userId)
    {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }
    @PutMapping("/change-password")
    public ResponseEntity<Long>changeUserAccountPassword
            (@Valid @RequestBody PasswordChangeRequest passwordChangeRequest)
    {
        return ResponseEntity.ok(
                userService.changeUserAccountPassword(passwordChangeRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<UserDetailsResponse> activateUser( @PathVariable Long userId)
    {
        return ResponseEntity.ok(userService.activateUser(userId));
    }
    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<UserDetailsResponse> deactivateUser
            ( @PathVariable Long userId)
    {
        return ResponseEntity.ok(userService.deactivateUser(userId));
    }

    @PostMapping("/create-delivery")
    public ResponseEntity<Long> createDeliveryUser
            (@Valid @RequestBody DeliveryPersonCreationDetails deliveryPersonCreationDetails)
    {
        Long userId=userService.createDeliveryUser(deliveryPersonCreationDetails);

        return new ResponseEntity<>(userId,HttpStatus.CREATED);
    }

}
