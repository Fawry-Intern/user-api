package com.fawry.kafka.factory;

import com.fawry.kafka.dto.EventType;
import com.fawry.kafka.events.RegisterEvent;
import com.fawry.user_api.dto.auth.AuthenticationRequest;
import com.fawry.user_api.dto.auth.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class EventFactory {

    public Object getEvent(EventType type, Object data) {
        switch (type) {
            case REGISTER -> {
                return new RegisterEvent(((RegisterRequest) data).email());
            }
            default -> {
                throw new IllegalArgumentException("Type not supported");
            }
        }
    }
}
