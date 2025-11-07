package com.airlineshub.AirlinesHub.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.airlineshub.AirlinesHub.model.Transaction;
import com.airlineshub.AirlinesHub.service.FailService;
import com.airlineshub.AirlinesHub.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airlineshub.AirlinesHub.model.Travel;

@RestController
@RequestMapping("/airlineshub")
public class AirlinesHubController {

    private final FlightService flightService;
    private final FailService failService;

    public AirlinesHubController(FlightService flightService, FailService failService) {
        this.flightService = flightService;
        this.failService = failService;
    }

    @GetMapping("/flight")
    public ResponseEntity<Travel> checkTravel(@RequestParam String flight, @RequestParam LocalDate day) {

        failService.createChanceFailOmission(0.2);

        Travel travel = flightService.findVoo(flight, day);

        return ResponseEntity.ok(travel);
    } 

    @PostMapping("/sell")
    public ResponseEntity<Transaction> sellTravel(@RequestParam String flight, @RequestParam LocalDate day) {

        failService.createChanceFailTime(5, 0.1, 10);

        flightService.findVoo(flight, day);
        Transaction transaction = new Transaction(UUID.randomUUID().toString());

        return ResponseEntity.ok(transaction);
    }   

    @GetMapping("/all")
    public ResponseEntity<List<Travel>> getAllTravels() {
        List<Travel> travels = flightService.findAll();

        return ResponseEntity.ok(travels);
    }    
}
