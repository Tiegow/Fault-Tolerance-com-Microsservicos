package com.airlineshub.AirlinesHub.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

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

    private AtomicBoolean isSlow = new AtomicBoolean(false);
    private AtomicLong slowStateEndTime = new AtomicLong();

    @GetMapping("/flight")
    public ResponseEntity<Voo> consultarVoo(@RequestParam String flight, @RequestParam LocalDate day) {

        // -- FALHA (Omissão, 0.2%, 0s) -- \\
        if (Math.random() < 0.2) {
            return ResponseEntity.ok(null);
        }

        // -- EXECUÇÃO NORMAL -- \\
        Voo voo = flightRepository.findVoo(flight, day);
        return ResponseEntity.ok(voo);
    } 

    @PostMapping("/sell")
    public ResponseEntity<String> venderVoo(@RequestParam String flight, @RequestParam LocalDate day) {
        
        long currTime = System.currentTimeMillis();
        
        // -- FALHA (Tempo = 5s, 0.1%, 10s) -- \\
        if (isSlow.get()) {
            if (currTime < slowStateEndTime.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                isSlow.set(false);
            }
        }

        if (Math.random() < 0.1 && isSlow.get() == false) { // 10% de chance de entrar no estado de lentidão
            isSlow.set(true);
            slowStateEndTime.set(currTime + 10000);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
        }
        
        // -- EXECUÇÃO NORMAL -- \\
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
