package com.fawry.user_api.service;

import com.fawry.user_api.dto.AuthenticationResponse;
import com.fawry.user_api.dto.PasswordChangeRequest;
import com.fawry.user_api.dto.UserResponse;

import java.util.List;

public interface UserService {

    //admin authorities only

   List<UserResponse> findAllUsers();

   UserResponse activateUser(Long userId);

   UserResponse deactivateUser(Long userId);



   //common user activities
   Long changeUserPassword(PasswordChangeRequest passwordChangeRequest);
    UserResponse getUserProfile(Long userId);

    Boolean removeAccount(Long userId);

}
