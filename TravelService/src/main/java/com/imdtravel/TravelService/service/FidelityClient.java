package com.imdtravel.TravelService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.imdtravel.TravelService.dto.FidelityBonusDTO;

@Service
public class FidelityClient {

    private final RestTemplate restTemplate;
    
    private final String fidelityServiceUrl; 

    @Autowired
    public FidelityClient(RestTemplate restTemplate, @Value("${app.services.fidelity.url}") String fidelityApiUrl) {
        this.restTemplate = restTemplate;
        this.fidelityServiceUrl = fidelityApiUrl;
    }

    public boolean createBonus(FidelityBonusDTO bonusDTO) {
        String url = fidelityServiceUrl + "/bonus";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        
        map.add("user", bonusDTO.user());
        map.add("bonus", String.valueOf(bonusDTO.bonus()));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        try {
            restTemplate.postForEntity(url, requestEntity, Void.class);
            
            return true;
            
        } catch (RestClientException e) {
            System.err.println("Erro ao chamar serviço de fidelidade: " + e.getMessage());
            return false;
        }
    }
}
