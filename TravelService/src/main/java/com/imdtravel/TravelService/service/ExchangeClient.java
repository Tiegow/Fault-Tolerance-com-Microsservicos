package com.imdtravel.TravelService.service;

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

    public Double getExchange(String from, String to) {
        String url = exchangeServiceUrl + "/exchange/convert";

        try {
            Double taxa = restTemplate.getForObject(url, Double.class);
            return taxa;
        } catch (Exception e) {
            System.err.println("Erro ao chamar ExchangeService: " + e.getMessage());
            return null;
        }
    }    
}
