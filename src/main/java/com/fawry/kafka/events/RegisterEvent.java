package com.fawry.kafka.events;

import java.io.Serializable;

public class RegisterEvent extends BaseEvent implements Serializable {

    private final String username;

    public RegisterEvent(String email, String username) {
        super(email);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RegisterEvent{" +
                "email='" + super.getEmail() +
                "username='" + username + '\'' +
                '}';
    }
}
