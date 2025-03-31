package com.fawry.user_api.dto.delivery_person;


import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public record DeliveryPersonCreationDetails(
        String firstName,
        String lastName,
        String email,
        String address,
        String phoneNumber,
         String password,
         List<DayOfWeek> workDays
) {
}

