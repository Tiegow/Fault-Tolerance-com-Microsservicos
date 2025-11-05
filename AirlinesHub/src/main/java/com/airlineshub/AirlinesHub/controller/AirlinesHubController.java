package com.airlineshub.AirlinesHub.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.airlineshub.AirlinesHub.model.Transacao;
import com.airlineshub.AirlinesHub.service.FailService;
import com.airlineshub.AirlinesHub.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airlineshub.AirlinesHub.model.Voo;
import com.airlineshub.AirlinesHub.repository.FlightRepository;

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
    public ResponseEntity<Voo> consultarVoo(@RequestParam String flight, @RequestParam LocalDate day) {

        failService.createChanceFailOmission(0.2);

        Voo voo = flightService.findVoo(flight, day);

        return ResponseEntity.ok(voo);
    } 

    @PostMapping("/sell")
    public ResponseEntity<Transacao> venderVoo(@RequestParam String flight, @RequestParam LocalDate day) {

        failService.createChanceFailTime(5, 0.1, 10);

        flightService.findVoo(flight, day);
        Transacao transacao = new Transacao(UUID.randomUUID().toString());

        return ResponseEntity.ok(transacao);
    }   

    @GetMapping("/all")
    public ResponseEntity<List<Voo>> listarTodos() {
        List<Voo> voos = flightService.findAll();

        return ResponseEntity.ok(voos);
    }    
}
