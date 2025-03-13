package com.fawry.user_api.service;

import com.fawry.user_api.dto.PasswordChangeRequest;
import com.fawry.user_api.dto.PasswordResetRequest;
import com.fawry.user_api.dto.UserDetailsDTO;
import com.fawry.user_api.dto.UserResponse;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.exception.IllegalActionException;
import com.fawry.user_api.repository.UserRepository;
import com.fawry.user_api.util.PasswordValidationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;

 private final HttpServletRequest httpServletRequest;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
    }
    @Override
    public List<UserResponse> findAllUsers() {
        return  userRepository.findAll().stream().map(UserResponse::of).toList();
    }

    @Override
    public UserResponse getUserProfile(Long userId) {

        String authenticatedUserId=httpServletRequest.getHeader("UserId");

        User user=getUserEntity(userId);
        if(!isSameUser(user.getId(),parseUserId(authenticatedUserId)))
            throw new IllegalActionException("only admin can see other profiles");

       return new UserResponse(userId, user.getUsername(),user.getEmail(),user.getIsActive(),user.getRole());

    }



    @Transactional
    @Override
    public UserResponse activateUser(Long userId) {

        User user=getUserEntity(userId);
        String authenticatedUserId=httpServletRequest.getHeader("UserId");
        if(isSameUser(userId, parseUserId(authenticatedUserId)))
            throw new IllegalActionException("you can't activate your account");
        user.setIsActive(true);

        return  new UserResponse(userId, user.getUsername(),user.getEmail(),user.getIsActive(),user.getRole());
    }

    @Transactional
    @Override
    public UserResponse deactivateUser(Long userId) {
        User user=getUserEntity(userId);
        String authenticatedUserId=httpServletRequest.getHeader("UserId");

        if( isSameUser(userId,parseUserId(authenticatedUserId)))
            throw new IllegalActionException("you can't deactivate your account");
        user.setIsActive(false);

        return  new UserResponse(userId, user.getUsername(),user.getEmail(),user.getIsActive(),user.getRole());
    }

    @Transactional
    @Override
    public Long resetUserAccountPassword(PasswordResetRequest passwordResetRequest) {


        if (!PasswordValidationHelper.isValid(passwordResetRequest.newPassword())) {
            throw new IllegalActionException("Password does not meet security requirements");
        }
        User user=getUserEntity(passwordResetRequest.userId());

        String authenticatedUserId=httpServletRequest.getHeader("UserId");

        if(!isSameUser(user.getId(),parseUserId(authenticatedUserId)))
            throw new IllegalActionException("you can't reset other user account password");

        String newPassword= passwordResetRequest.newPassword();
        String confirmedPassword= passwordResetRequest.confirmedPassword();

        if(!confirmedPassword.equals(newPassword))
            throw new IllegalActionException("the two passwords aren't identical");
        user.setPassword(passwordEncoder.encode(newPassword));

        return user.getId();
    }


    @Transactional
    @Override
    public Long changeUserAccountPassword(PasswordChangeRequest passwordChangeRequest) {

        if (!PasswordValidationHelper.isValid(passwordChangeRequest.newPassword())) {
            throw new IllegalActionException("Password does not meet security requirements");
        }

        User user = getUserEntity(passwordChangeRequest.userId());

        String authenticatedUserId=httpServletRequest.getHeader("UserId");

        if (!isSameUser(user.getId(),parseUserId(authenticatedUserId))) {
            throw new IllegalActionException("You can't change another user's account password");
        }


        if (!passwordEncoder.matches(passwordChangeRequest.oldPassword(), user.getPassword())) {
            throw new ValidationException("Old password isn't correct");
        }


        String encodedNewPassword = passwordEncoder.encode(passwordChangeRequest.newPassword());
        user.setPassword(encodedNewPassword);

        return user.getId();
    }


    private User getUserEntity(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User with id= "+userId+" doesn't exist"));
    }

   private Boolean isSameUser(Long userId,Long authenticatedUserId)
   {
       return  userId.equals(authenticatedUserId);
   }
   private Long parseUserId (String id)
   {
       if (id == null) {
           throw new IllegalActionException("UserId header is missing");
       }

       Long authUserId;
       try {
           authUserId = Long.parseLong(id);
       } catch (NumberFormatException e) {
           throw new IllegalActionException("Invalid UserId format");
       }
       return authUserId;
   }

}
