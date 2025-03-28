package com.fawry.user_api.service.Impl;

import com.fawry.kafka.dto.EventType;
import com.fawry.kafka.events.BaseEvent;
import com.fawry.kafka.events.RegisterEvent;
import com.fawry.kafka.factory.EventFactory;
import com.fawry.kafka.producer.Producer;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.user.PasswordChangeRequest;
import com.fawry.user_api.dto.user.PasswordResetRequest;

import com.fawry.user_api.dto.user.UserDetailsResponse;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import com.fawry.user_api.exception.DuplicateResourceException;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.exception.IllegalActionException;
import com.fawry.user_api.exception.ResourceType;
import com.fawry.user_api.mapper.AuthenticationMapper;
import com.fawry.user_api.mapper.UserMapper;
import com.fawry.user_api.repository.UserRepository;
import com.fawry.user_api.service.UserService;
import com.fawry.user_api.util.PasswordValidationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationMapper authenticationMapper;
    private final HttpServletRequest httpServletRequest;
    private final Producer<BaseEvent> producer;

    private final EventFactory eventFactory;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationMapper authenticationMapper, HttpServletRequest httpServletRequest, Producer<BaseEvent> producer, EventFactory eventFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationMapper = authenticationMapper;
        this.httpServletRequest = httpServletRequest;
        this.producer = producer;
        this.eventFactory = eventFactory;
    }
    @Override
    public List<UserDetailsResponse> findAllUsers() {
        return  userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserDetailsResponse getUserProfile(Long userId) {

       Long authenticatedUserId=parseUserId(httpServletRequest.getHeader("UserId"));

     UserRole authenticatedUserRole= UserRole.valueOf(httpServletRequest.getHeader("Role"));
        User user=getUserEntity(userId);

        if(!authenticatedUserRole.equals(UserRole.ADMIN) && !isSameUser(user.getId(),authenticatedUserId))
            throw new IllegalActionException("only admin can see other profiles");

       return userMapper.toUserResponse(user);

    }



    @Transactional
    @Override
    public UserDetailsResponse activateUser(Long userId) {

        User user=getUserEntity(userId);
        String authenticatedUserId=httpServletRequest.getHeader("UserId");
        if(isSameUser(userId, parseUserId(authenticatedUserId)))
            throw new IllegalActionException("you can't activate your account");
        user.setIsActive(true);

        return  userMapper.toUserResponse(user);
    }

    @Transactional
    @Override
    public UserDetailsResponse deactivateUser(Long userId) {
        User user=getUserEntity(userId);
        String authenticatedUserId=httpServletRequest.getHeader("UserId");

        if( isSameUser(userId,parseUserId(authenticatedUserId)))
            throw new IllegalActionException("you can't deactivate your account");
        user.setIsActive(false);

        return  userMapper.toUserResponse(user);
    }

    @Override
    public void createDeliveryUser(RegisterRequest registerRequest) {
        User user = authenticationMapper.mapFromSignRequestToDelivery(registerRequest);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("this username already exists", ResourceType.USERNAME);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("This email already exists", ResourceType.EMAIL);
        }

        userRepository.save(user);


        var event = (RegisterEvent) eventFactory.getEvent(EventType.REGISTER, registerRequest);

        producer.sendEvent(event, 0);
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
