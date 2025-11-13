package com.imdtravel.TravelService.service.AirlinesHub;

import java.time.LocalDate;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.exception.NotFoundException;
import com.imdtravel.TravelService.model.AirlinesHubClient;
import com.imdtravel.TravelService.model.Transaction;
import com.imdtravel.TravelService.model.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AirlinesHubClientWithoutFT implements AirlinesHubClient {

    private final RestTemplate restTemplate;
    private final String hubServiceUrl; 

    @Autowired
    public AirlinesHubClientWithoutFT(RestTemplate restTemplate, @Value("${app.services.hub.url}") String hubApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.hubServiceUrl = hubApiBaseUrl;
    }

    public Travel checkTravel(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/flight")
                .queryParam("flight", flight)
                .queryParam("day", day.toString())
                .toUriString();

        try {
            Travel travel = restTemplate.getForObject(url, Travel.class);

            return travel;
        } catch (Exception e) {
            throw new NotFoundException("Recurso não encontrado no serviço de linhas aéreas!");
        }
    }

    public Transaction sellTravel(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/sell")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        try {
            Transaction transaction = restTemplate.postForObject(url, null, Transaction.class);

            return transaction;
        } catch (Exception e) {
            if(e instanceof NotFoundException) {
                throw new NotFoundException(e.getMessage());
            } else {
                throw new ExternalServiceException("Ocorreu um erro interno ao tentar realizar a venda no serviço de linhas aéreas!");
            }
        }
    }
}
