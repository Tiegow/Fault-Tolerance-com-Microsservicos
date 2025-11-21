package com.imdtravel.TravelService.service.Exchange;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.model.ExchangeClient;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeClientWithFT implements ExchangeClient {

    private final RestTemplate restTemplate;
    
    private final String exchangeServiceUrl;

    private final Deque<Double> ultimasDezTaxas = new LinkedBlockingDeque<>(10);

    public ExchangeClientWithFT(@Qualifier("restTemplateWithFT") RestTemplate restTemplate, @Value("${app.services.exchange.url}") String exchangeServiceUrl) {
        this.restTemplate = restTemplate;
        this.exchangeServiceUrl = exchangeServiceUrl;
    }

    public Double getExchangeRate() {
        String url = exchangeServiceUrl + "/convert";

        try {
            Double taxa = restTemplate.getForObject(url, Double.class);

            if (taxa == null) {
                throw new ExternalServiceException("Serviço de conversão retornou valor nulo.");
            }

            if (!ultimasDezTaxas.offerLast(taxa)) {
                ultimasDezTaxas.pollFirst();
                ultimasDezTaxas.offerLast(taxa);
            }

            return taxa;
        } catch (Exception e) {
            if (!ultimasDezTaxas.isEmpty()) {
                double soma = 0.0;
                int contador = 0;
                for (Double taxaCache : ultimasDezTaxas) {
                    if (taxaCache != null) {
                        soma += taxaCache;
                        contador++;
                    }
                }
                if (contador > 0) {
                    return soma / contador;
                }
            }

            throw new ExternalServiceException("Houve um problema no serviço que fornece a taxa de conversão. Tente novamente mais tarde.");
        }
    } 
}
