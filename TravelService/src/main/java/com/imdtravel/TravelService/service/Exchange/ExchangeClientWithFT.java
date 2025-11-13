package com.imdtravel.TravelService.service.Exchange;

import com.imdtravel.TravelService.model.ExchangeClient;
import org.springframework.stereotype.Service;

@Service
public class ExchangeClientWithFT implements ExchangeClient {
    @Override
    public Double getExchangeRate() {
        return 0.0;
    }
}
