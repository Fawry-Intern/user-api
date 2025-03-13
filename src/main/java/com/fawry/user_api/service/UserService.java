package com.fawry.user_api.service;

import com.fawry.user_api.dto.*;
import com.fawry.user_api.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    //admin authorities only

   List<UserResponse> findAllUsers();

   UserResponse activateUser(Long userId);

   UserResponse deactivateUser(Long userId);



   //common user activities
    Long resetUserAccountPassword(PasswordResetRequest passwordResetRequest);
   Long changeUserAccountPassword(PasswordChangeRequest passwordChangeRequest);
    UserResponse getUserProfile(Long userId);



}
