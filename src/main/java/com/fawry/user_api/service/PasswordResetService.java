package com.fawry.user_api.service;

import com.fawry.user_api.dto.user.PasswordChangeRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;

import java.security.NoSuchAlgorithmException;

public interface PasswordResetService {

    Boolean passwordResetRequest(String email) throws NoSuchAlgorithmException;


    Boolean resetPassword(PasswordResetRequest passwordResetRequest) throws NoSuchAlgorithmException;

}
