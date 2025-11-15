package com.imdtravel.TravelService.service.AirlinesHub;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.model.AirlinesHubClient;
import com.imdtravel.TravelService.model.Transaction;
import com.imdtravel.TravelService.model.Travel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class AirlinesHubClientWithFT implements AirlinesHubClient {

    private final RestTemplate restTemplate;
    private final String hubServiceUrl; 

    @Autowired
    public AirlinesHubClientWithFT(RestTemplate restTemplate, @Value("${app.services.hub.url}") String hubApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.hubServiceUrl = hubApiBaseUrl;
    }

    @Override
    public Travel checkTravel(String flight, LocalDate day) {
        return new Travel("0", LocalDate.now(), 5.0);
    }

    @Override
    @CircuitBreaker(name = "sellTravelBreaker", fallbackMethod = "sellTravelFallback")
    public Transaction sellTravel(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/sell")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        Transaction transaction = restTemplate.postForObject(url, null, Transaction.class);
        return transaction;
    }

    public Transaction sellTravelFallback(String flight, LocalDate day, Throwable t) {
        throw new ExternalServiceException("Serviço de venda (AirlinesHub) indisponível no momento. Tente mais tarde.");
    }
}
