package com.exchange.ExchangeService.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class FailService {

    private AtomicBoolean errorState = new AtomicBoolean(false);
    private AtomicLong errorStateEndTime = new AtomicLong(0);

    public void createChanceFailError(Double chance, Integer secondsFailDuration) {
        long currTime = System.currentTimeMillis();

        if (errorState.get()) {
            if (currTime < errorStateEndTime.get()) {
                throw new RuntimeException("Erro simulado pelo serviço de Exchange.");
            } else {
                errorState.set(false);
            }
        }

        if (Math.random() < chance && !errorState.get()) {
            errorState.set(true);
            errorStateEndTime.set(currTime + secondsFailDuration * 1000);

            throw new RuntimeException("Erro simulado pelo serviço de Exchange.");
        }
    }
}
