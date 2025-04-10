package com.fawry.user_api.service;

import com.fawry.user_api.dto.delivery_person.DeliveryPersonCreationDetails;

import java.util.List;

public interface WebClientService {

    Long createDeliveryPerson(DeliveryPersonCreationDetails details);


}
