package com.imdtravel.TravelService.service;

import com.imdtravel.TravelService.model.*;
import com.imdtravel.TravelService.service.AirlinesHub.AirlinesHubClientFactory;
import com.imdtravel.TravelService.service.Exchange.ExchangeClientFactory;
import com.imdtravel.TravelService.service.Fidelity.FidelityClientFactory;
import com.imdtravel.TravelService.service.Fidelity.FidelityClientWithoutFT;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Fidelity;
import java.time.LocalDate;

@Service
public class IMDTravelService {

    private final AirlinesHubClientFactory airlinesHubClientFactory;
    private final ExchangeClientFactory exchangeClientFactory;
    private final FidelityClientFactory fidelityClientFactory;

    public IMDTravelService(AirlinesHubClientFactory airlinesHubClientFactory, ExchangeClientFactory exchangeClientFactory, FidelityClientFactory fidelityClientFactory) {
        this.airlinesHubClientFactory = airlinesHubClientFactory;
        this.exchangeClientFactory = exchangeClientFactory;
        this.fidelityClientFactory = fidelityClientFactory;
    }

    public Transaction buyTicket(String flight, LocalDate day, String user, Boolean ft) {
        AirlinesHubClient airlinesHubClient = airlinesHubClientFactory.get(ft);
        Travel voo = airlinesHubClient.checkTravel(flight, day);

        ExchangeClient exchangeClient = exchangeClientFactory.get(ft);
        Double exchangeRate = exchangeClient.getExchangeRate();

        Transaction compra = airlinesHubClient.sellTravel(voo.getFlight(), voo.getDay());

        FidelityClient fidelityClient = fidelityClientFactory.get(ft);
        int bonus = (int) Math.round(voo.getValue());
        fidelityClient.createBonus(new FidelityBonus(user, bonus));

        return compra;
    }
}
