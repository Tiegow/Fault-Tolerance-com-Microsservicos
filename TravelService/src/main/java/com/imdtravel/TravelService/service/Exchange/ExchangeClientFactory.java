package com.imdtravel.TravelService.service.Exchange;

import com.imdtravel.TravelService.model.ExchangeClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ExchangeClientFactory {

    private final ExchangeClient withFT;
    private final ExchangeClient withoutFT;

    public ExchangeClientFactory(@Qualifier("exchangeClientWithFT") ExchangeClient withFT, @Qualifier("exchangeClientWithoutFT") ExchangeClient withoutFT) {
        this.withFT = withFT;
        this.withoutFT = withoutFT;
    }

    public ExchangeClient get(Boolean ft) {
        return ft? withFT : withoutFT;
    }
}
