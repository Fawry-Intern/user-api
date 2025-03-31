package com.fawry.user_api.service.Impl;

import com.fawry.kafka.dto.EventType;
import com.fawry.kafka.events.BaseEvent;
import com.fawry.kafka.events.RegisterEvent;
import com.fawry.kafka.factory.EventFactory;
import com.fawry.kafka.producer.Producer;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.dto.delivery_person.DeliveryPersonCreationDetails;
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
import com.fawry.user_api.service.WebClientService;
import com.fawry.user_api.util.PasswordValidationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationMapper authenticationMapper;
    private final HttpServletRequest httpServletRequest;
    private final Producer<BaseEvent> producer;
    private  final WebClientService webClientService;
    private final EventFactory eventFactory;


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
    public Long createDeliveryUser(DeliveryPersonCreationDetails deliveryPersonCreationDetails) {
        User user = authenticationMapper.mapFromSignRequestToDelivery(deliveryPersonCreationDetails);


        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("This email already exists", ResourceType.EMAIL);
        }

        webClientService.createDeliveryPerson(deliveryPersonCreationDetails);

        User savedUser= userRepository.save(user);
          RegisterRequest registerRequest= RegisterRequest
                  .builder().firstName(deliveryPersonCreationDetails.firstName())
                  .lastName(deliveryPersonCreationDetails.lastName())
                  .email(deliveryPersonCreationDetails.email())
                  .password(deliveryPersonCreationDetails.password())
                  .build();

        var event = (RegisterEvent) eventFactory.getEvent(EventType.REGISTER, registerRequest);
        producer.sendEvent(event, 0);

        return savedUser.getId();

    }

    @Transactional
    @Override
    public Long changeUserAccountPassword(PasswordChangeRequest passwordChangeRequest) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedUserId = httpServletRequest.getHeader("UserId");

        if (!isSameUser(authenticatedUser.getId(), parseUserId(authenticatedUserId))) {
            throw new IllegalActionException("You can't change another user's account password");
        }

        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

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
