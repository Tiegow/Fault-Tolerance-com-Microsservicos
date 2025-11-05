package com.airlineshub.AirlinesHub.service;

import com.airlineshub.AirlinesHub.exception.NotFoundException;
import com.airlineshub.AirlinesHub.model.Voo;
import com.airlineshub.AirlinesHub.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Voo findVoo(String flightCode, LocalDate day) {
        return this.flightRepository.getVoos().stream()
                .filter(voo -> voo.getFlight().equalsIgnoreCase(flightCode) && voo.getDay().equals(day))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Recurso não encontrado"));
    }

    public List<Voo> findAll() {
        return this.flightRepository.getVoos();
    }
}
