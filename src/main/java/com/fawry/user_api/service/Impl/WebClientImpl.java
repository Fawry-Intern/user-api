package com.fawry.user_api.service.Impl;

import com.fawry.user_api.dto.delivery_person.DeliveryPersonCreationDetails;
import com.fawry.user_api.service.WebClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class WebClientImpl implements WebClientService {

    private final WebClient.Builder webClientBuilder;

    private WebClient webClient() {
        return webClientBuilder.baseUrl("http://localhost:5555").build();
    }

    @Override
    public Long createDeliveryPerson(DeliveryPersonCreationDetails details) {
        try {
            return webClient()
                    .post()
                    .uri("/api/delivery/create-delivery")
                    .bodyValue(details)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ResponseStatusException(
                    HttpStatus.valueOf(e.getStatusCode().value()),
                    "Error creating delivery person: " + e.getMessage(),
                    e
            );
        }
    }

}

