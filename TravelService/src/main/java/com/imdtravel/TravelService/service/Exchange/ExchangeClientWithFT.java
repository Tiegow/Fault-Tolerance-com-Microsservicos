package com.imdtravel.TravelService.service.Exchange;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.model.ExchangeClient;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeClientWithFT implements ExchangeClient {

    private final RestTemplate restTemplate;
    
    private final String exchangeServiceUrl;

    private final LinkedList<Double> ultimasDezTaxas = new LinkedList<>();
    private static final int CACHE_MAXIMO = 10;

    public ExchangeClientWithFT(@Qualifier("restTemplateWithFT") RestTemplate restTemplate, @Value("${app.services.exchange.url}") String exchangeServiceUrl) {
        this.restTemplate = restTemplate;
        this.exchangeServiceUrl = exchangeServiceUrl;
    }

    public Double getExchangeRate() {
        String url = exchangeServiceUrl + "/convert";

        try {
            Double taxa = restTemplate.getForObject(url, Double.class);

            if (ultimasDezTaxas.size() >= CACHE_MAXIMO) {
                ultimasDezTaxas.removeFirst();
            }

            ultimasDezTaxas.addLast(taxa);

            return taxa;
        } catch (HttpStatusCodeException e) {
            if (!ultimasDezTaxas.isEmpty()) {
                double soma = 0.0;
                for (Double taxaCache : ultimasDezTaxas) {
                    soma += taxaCache;
                }
                return soma / ultimasDezTaxas.size();
            }

            throw new ExternalServiceException("Houve um problema no serviço que fornece a taxa de conversão. Tente novamente mais tarde.");
        }
    } 
}
