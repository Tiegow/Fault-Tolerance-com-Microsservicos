package com.airlineshub.AirlinesHub.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/flight")
    public ResponseEntity<Voo> consultarVoo(@RequestParam String flight, @RequestParam LocalDate day) {
        Voo voo = flightRepository.findVoo(flight, day);

        return ResponseEntity.ok(voo);
    } 

    @PostMapping("/sell")
    public ResponseEntity<String> venderVoo(@RequestParam String flight, @RequestParam LocalDate day) {
        Voo voo = flightRepository.findVoo(flight, day);

        if (voo != null) {
            String idTransacao = UUID.randomUUID().toString();

            return ResponseEntity.ok(idTransacao);
        }
        
        return ResponseEntity.notFound().build();
    }   

    @GetMapping("/all")
    public ResponseEntity<List<Voo>> listarTodos() {
        List<Voo> voos = flightRepository.findAll();

        return ResponseEntity.ok(voos);
    }    
}
