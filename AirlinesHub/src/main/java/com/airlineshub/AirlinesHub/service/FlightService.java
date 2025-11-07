package com.airlineshub.AirlinesHub.service;

import com.airlineshub.AirlinesHub.exception.NotFoundException;
import com.airlineshub.AirlinesHub.model.Travel;
import com.airlineshub.AirlinesHub.repository.TravelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {

    private final TravelRepository travelRepository;

    public FlightService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public Travel findVoo(String flightCode, LocalDate day) {
        return this.travelRepository.getTravels().stream()
                .filter(travel -> travel.getFlight().equalsIgnoreCase(flightCode) && travel.getDay().equals(day))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Recurso não encontrado"));
    }

    public List<Travel> findAll() {
        return this.travelRepository.getTravels();
    }
}
