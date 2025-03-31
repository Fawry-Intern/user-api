package com.fawry.user_api.service;

import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.delivery_person.DeliveryPersonCreationDetails;
import com.fawry.user_api.dto.user.PasswordChangeRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;
import com.fawry.user_api.dto.user.UserDetailsResponse;

import java.util.List;

public interface UserService {

    //admin authorities only

   List<UserDetailsResponse> findAllUsers();

   UserDetailsResponse activateUser(Long userId);

   UserDetailsResponse deactivateUser(Long userId);

   Long createDeliveryUser(DeliveryPersonCreationDetails registerRequest);



   //common user activities

    Long changeUserAccountPassword(PasswordChangeRequest passwordChangeRequest);
    UserDetailsResponse getUserProfile(Long userId);



}
