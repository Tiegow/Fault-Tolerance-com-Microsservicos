package com.imdtravel.TravelService.service.AirlinesHub;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.exception.NotFoundException;
import com.imdtravel.TravelService.exception.UnavaliableServiceException;
import com.imdtravel.TravelService.model.AirlinesHubClient;
import com.imdtravel.TravelService.model.Transaction;
import com.imdtravel.TravelService.model.Travel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class AirlinesHubClientWithFT implements AirlinesHubClient {

    private final RestTemplate restTemplate;
    private final String hubServiceUrl; 

    public AirlinesHubClientWithFT(@Qualifier("restTemplateWithFT") RestTemplate restTemplate, @Value("${app.services.hub.url}") String hubApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.hubServiceUrl = hubApiBaseUrl;
    }

    @Override
    @Retry(name = "checkTravelRetry", fallbackMethod = "checkTravelFallback")
    public Travel checkTravel(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/flight")
                .queryParam("flight", flight)
                .queryParam("day", day.toString())
                .toUriString();

        try {
            Travel travel = restTemplate.getForObject(url, Travel.class);

            return travel;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Recurso não encontrado no serviço de linhas aéreas.");
            } else {
                throw new ExternalServiceException("Houve um problema no serviço de checagem de linhas aéreas. Tente novamente mais tarde.");
            }
        }
    }

    private Travel checkTravelFallback(String flight, LocalDate day, Throwable ex) {
        if (ex instanceof NotFoundException) {
            throw (NotFoundException) ex;
        }

        throw new UnavaliableServiceException("Serviço de checagem de linhas aéreas indisponível no momento. Tente novamente mais tarde.");
    }

    @Override
    @CircuitBreaker(name = "sellTravelBreaker", fallbackMethod = "sellTravelFallback")
    public Transaction sellTravel(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/sell")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        try {
            Transaction transaction = restTemplate.postForObject(url, null, Transaction.class);

            return transaction;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Recurso não encontrado no serviço de linhas aéreas.");
            } else {
                throw new ExternalServiceException("Houve um problema no serviço de venda de linhas aéreas. Tente novamente mais tarde.");
            }
        }
    }

    private Transaction sellTravelFallback(String flight, LocalDate day, Throwable ex) {
        if (ex instanceof NotFoundException) {
            throw (NotFoundException) ex;
        }

        throw new UnavaliableServiceException("Serviço de venda de linhas aéreas indisponível no momento. Tente mais tarde.");
    }
}
