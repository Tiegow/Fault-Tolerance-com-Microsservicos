package com.airlineshub.AirlinesHub.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.airlineshub.AirlinesHub.model.Voo;

@Repository
public class FlightRepository {

    private List<Voo> voos;

    public FlightRepository() {
        this.voos = new ArrayList<>(Arrays.asList(
            new Voo("G3-1234", LocalDate.of(2025, 11, 10), 1200.50),
            new Voo("AD-4567", LocalDate.of(2025, 11, 12), 950.00),
            new Voo("TP-008", LocalDate.of(2025, 11, 10), 3400.75),
            new Voo("JA-1710", LocalDate.of(2025, 12, 25), 3400.75),
            new Voo("JK-6666", LocalDate.of(2026, 01, 15), 3400.75)
        ));
    }

    public Voo findVoo(String flightCode, LocalDate day) {
        return this.voos.stream()
            .filter(voo -> voo.getFlight().equalsIgnoreCase(flightCode) && voo.getDay().equals(day))
            .findFirst()
            .orElse(null); 
    }
    
    public List<Voo> findAll() {
        return this.voos;
    }
}
