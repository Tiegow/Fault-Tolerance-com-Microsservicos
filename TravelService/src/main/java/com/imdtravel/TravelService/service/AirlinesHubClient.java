package com.imdtravel.TravelService.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.imdtravel.TravelService.dto.VooDTO;

@Service
public class AirlinesHubClient {

    private final RestTemplate restTemplate;
    private final String hubServiceUrl; 

    @Autowired
    public AirlinesHubClient(RestTemplate restTemplate, @Value("${app.services.hub.url}") String hubApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.hubServiceUrl = hubApiBaseUrl;
    }

    public VooDTO consultarVoo(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/flight")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        try {
            VooDTO voo = restTemplate.getForObject(url, VooDTO.class);
            return voo;
        } catch (RestClientException e) {
            System.err.println("Erro ao consultar voo no Hub: " + e.getMessage());
            return null; 
        }
    }

    public String venderVoo(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/sell")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        try {
            String transactionId = restTemplate.postForObject(url, null, String.class);
            
            return transactionId;

        } catch (RestClientException e) {
            System.err.println("Erro ao realizar venda no Hub (voo pode não existir): " + e.getMessage());
            return null;
        }
    }
}
