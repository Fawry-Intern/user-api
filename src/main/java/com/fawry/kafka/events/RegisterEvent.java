package com.fawry.kafka.events;

public class RegisterEvent extends BaseEvent {


    public RegisterEvent(String email) {
        super(email);

    }



    @Override
    public String toString() {
        return "RegisterEvent{" +
                "email='" + super.getEmail() +
                "username='"  + '\'' +
                '}';
    }
}
