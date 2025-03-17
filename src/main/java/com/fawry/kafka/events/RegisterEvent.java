package com.fawry.kafka.events;

public class RegisterEvent extends BaseEvent {

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
