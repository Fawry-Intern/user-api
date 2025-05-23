package com.fawry.user_api.service.Impl;

import com.fawry.kafka.dto.EventType;
import com.fawry.kafka.events.BaseEvent;
import com.fawry.kafka.events.RegisterEvent;
import com.fawry.kafka.factory.EventFactory;
import com.fawry.kafka.producer.Producer;
import com.fawry.user_api.dto.auth.AuthenticationResponse;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;
import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import com.fawry.user_api.exception.DuplicateResourceException;
import com.fawry.user_api.exception.EntityNotFoundException;
import com.fawry.user_api.exception.IllegalActionException;
import com.fawry.user_api.exception.ResourceType;
import com.fawry.user_api.mapper.AuthenticationMapper;
import com.fawry.user_api.repository.UserRepository;
import com.fawry.user_api.security.JwtService;
import com.fawry.user_api.service.AuthenticationService;
import com.fawry.user_api.service.WebClientService;
import com.fawry.user_api.util.PasswordValidationHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthenticationMapper authenticationMapper;
    private final Producer<BaseEvent> producer;

    private final EventFactory eventFactory;


    @Override
    public Boolean register(RegisterRequest request) {

        User user = authenticationMapper.mapFromSignRequestToCustomer(request);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("This email already exists", ResourceType.EMAIL);
        }

        userRepository.save(user);


        var event = (RegisterEvent) eventFactory.getEvent(EventType.REGISTER, request);

        producer.sendEvent(event, 0);

        return true;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        User user=userRepository.findByEmail(request.email()).orElseThrow(()->new EntityNotFoundException("user not found"));
        String token=jwtService.generateToken(user);


        return authenticationMapper.toAuthResponse(token, user.getId(),user.getRole(),user.getEmail());
    }
}
