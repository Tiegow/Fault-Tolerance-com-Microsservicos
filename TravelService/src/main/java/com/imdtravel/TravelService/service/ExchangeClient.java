package com.imdtravel.TravelService.service;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeClient {

    private final RestTemplate restTemplate;
    
    private final String exchangeServiceUrl;

    @Autowired
    public ExchangeClient(RestTemplate restTemplate, @Value("${app.services.exchange.url}") String exchangeServiceUrl) {
        this.restTemplate = restTemplate;
        this.exchangeServiceUrl = exchangeServiceUrl;
    }

    public Double getExchangeRate() {
        String url = exchangeServiceUrl + "/convert";

        try {
            Double taxa = restTemplate.getForObject(url, Double.class);

            return taxa;
        } catch (Exception e) {
            throw new ExternalServiceException("Ocorreu um erro interno no serviço que fornece a taxa de conversão!");
        }
    }    
}
