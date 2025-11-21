package com.imdtravel.TravelService.service.Fidelity;

import com.imdtravel.TravelService.exception.ExternalServiceException;
import com.imdtravel.TravelService.model.FidelityBonus;
import com.imdtravel.TravelService.model.FidelityClient;
import com.imdtravel.TravelService.service.Fidelity.Queue.BonusQueueService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class FidelityClientWithFT implements FidelityClient {
    private final RestTemplate restTemplate;

    private final String fidelityServiceUrl;
    private final BonusQueueService bonusQueueService;

    public FidelityClientWithFT(@Qualifier("restTemplateWithFT") RestTemplate restTemplate, @Value("${app.services.fidelity.url}") String fidelityServiceUrl, BonusQueueService bonusQueueService) {
        this.restTemplate = restTemplate;
        this.fidelityServiceUrl = fidelityServiceUrl;
        this.bonusQueueService = bonusQueueService;
    }

    @CircuitBreaker(name = "createBonusBreaker", fallbackMethod = "createBonusFallback")
    public void createBonus(FidelityBonus bonus) {
        String url = fidelityServiceUrl + "/bonus";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("user", bonus.user());
        map.add("bonus", String.valueOf(bonus.bonus()));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        try {
            restTemplate.postForEntity(url, requestEntity, Void.class);
        } catch (Exception e) {
            throw new ExternalServiceException("Houve um problema no serviço de fidelidade. Tente novamente mais tarde.");
        }
    }

    public void  createBonusFallback(FidelityBonus bonus, Throwable ex) {
        bonusQueueService.add(bonus);
    }
}
