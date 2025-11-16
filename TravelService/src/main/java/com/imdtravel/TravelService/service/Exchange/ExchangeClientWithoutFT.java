package com.imdtravel.TravelService.service.Exchange;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.model.ExchangeClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeClientWithoutFT implements ExchangeClient {

    private final RestTemplate restTemplate;
    
    private final String exchangeServiceUrl;

    public ExchangeClientWithoutFT(@Qualifier("restTemplateWithoutFT") RestTemplate restTemplate, @Value("${app.services.exchange.url}") String exchangeServiceUrl) {
        this.restTemplate = restTemplate;
        this.exchangeServiceUrl = exchangeServiceUrl;
    }

    public Double getExchangeRate() {
        String url = exchangeServiceUrl + "/convert";

        try {
            Double taxa = restTemplate.getForObject(url, Double.class);

            return taxa;
        } catch (HttpStatusCodeException e) {
            throw new ExternalServiceException("Ocorreu um erro interno no serviço que fornece a taxa de conversão.");
        }
    }    
}
