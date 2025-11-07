package com.exchange.ExchangeService.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ExchangeService {

    public Double getExchangeRateToDollar() {
        return ThreadLocalRandom.current().nextDouble(5, 6);
    };
}
