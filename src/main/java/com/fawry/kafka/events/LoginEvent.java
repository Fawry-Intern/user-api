package com.fawry.kafka.events;

public class LoginEvent extends BaseEvent{

    private final String password;
    public LoginEvent(String email, String password) {
        super(email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
