package com.exchange.ExchangeService.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.ExchangeService.service.FailService;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final FailService failService;

    public ExchangeController(FailService failService) {
        this.failService = failService;
    }

    @GetMapping("/convert")
    public double obterTaxaDolar() {

        failService.createChanceFailError(0.1, 5);

        double taxa = ThreadLocalRandom.current().nextDouble(5, 6);

        return taxa;
    }
}
