package com.fawry.user_api.service;

import com.fawry.user_api.dto.user.PasswordResetRequest;
import com.fawry.user_api.dto.user.UserDetailsResponse;

import java.util.List;

public interface UserService {

    //admin authorities only

   List<UserDetailsResponse> findAllUsers();

   UserDetailsResponse activateUser(Long userId);

   UserDetailsResponse deactivateUser(Long userId);



   //common user activities
    Long resetUserAccountPassword(PasswordResetRequest passwordResetRequest);
    UserDetailsResponse getUserProfile(Long userId);



}
