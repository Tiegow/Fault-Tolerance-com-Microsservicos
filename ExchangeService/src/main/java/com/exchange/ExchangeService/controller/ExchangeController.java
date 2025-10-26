package com.exchange.ExchangeService.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @GetMapping("/convert")
    public double obterTaxaDolar() {
        double taxa = ThreadLocalRandom.current().nextDouble(5, 6);

        return taxa;
    }
}
