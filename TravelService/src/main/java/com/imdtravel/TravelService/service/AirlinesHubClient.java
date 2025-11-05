package com.imdtravel.TravelService.service;

import java.time.LocalDate;

import com.imdtravel.TravelService.exception.NotFoundException;
import com.imdtravel.TravelService.model.Transacao;
import com.imdtravel.TravelService.model.Voo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AirlinesHubClient {

    private final RestTemplate restTemplate;
    private final String hubServiceUrl; 

    @Autowired
    public AirlinesHubClient(RestTemplate restTemplate, @Value("${app.services.hub.url}") String hubApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.hubServiceUrl = hubApiBaseUrl;
    }

    public Voo consultarVoo(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/flight")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        try {
            Voo voo = restTemplate.getForObject(url, Voo.class);

            return voo;
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public Transacao venderVoo(String flight, LocalDate day) {
        String url = UriComponentsBuilder.fromUriString(hubServiceUrl + "/sell")
                .queryParam("flight", flight)
                .queryParam("day", day.toString()) 
                .toUriString();

        try {
            Transacao transacao = restTemplate.postForObject(url, null, Transacao.class);
            
            return transacao;
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
