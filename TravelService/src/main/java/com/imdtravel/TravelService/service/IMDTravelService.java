package com.imdtravel.TravelService.service;

import com.imdtravel.TravelService.model.FidelityBonus;
import com.imdtravel.TravelService.model.Transaction;
import com.imdtravel.TravelService.model.Travel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IMDTravelService {

    private final AirlinesHubClient airlinesHubClient;
    private final ExchangeClient exchangeClient;
    private final FidelityClient fidelityClient;

    public IMDTravelService(AirlinesHubClient airlinesHubClient, ExchangeClient exchangeClient, FidelityClient fidelityClient) {
        this.airlinesHubClient = airlinesHubClient;
        this.exchangeClient = exchangeClient;
        this.fidelityClient = fidelityClient;
    }

    public Transaction buyTicket(String flight, LocalDate day, String user) {
        Travel voo = airlinesHubClient.checkTravel(flight, day);

        Double exchangeRate = exchangeClient.getExchangeRate();

        Transaction compra = airlinesHubClient.sellTravel(voo.getFlight(), voo.getDay());

        int bonus = (int) Math.round(voo.getValue());
        fidelityClient.createBonus(new FidelityBonus(user, bonus));

        return compra;
    }
}
