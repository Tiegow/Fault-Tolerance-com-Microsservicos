package com.exchange.ExchangeService.controller;

import com.exchange.ExchangeService.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.ExchangeService.service.FailService;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final FailService failService;
    private final ExchangeService exchangeService;

    public ExchangeController(FailService failService, ExchangeService exchangeService) {
        this.failService = failService;
        this.exchangeService = exchangeService;
    }

    @GetMapping("/convert")
    public ResponseEntity<Double> getExchangeRateToDollar() {

        failService.createChanceFailError(0.1, 5);

        Double exchangeRate = exchangeService.getExchangeRateToDollar();

        return ResponseEntity.ok().body(exchangeRate);
    }
}
