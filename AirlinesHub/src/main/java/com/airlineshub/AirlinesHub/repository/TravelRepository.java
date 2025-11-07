package com.airlineshub.AirlinesHub.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.airlineshub.AirlinesHub.model.Travel;

@Repository
public class TravelRepository {

    private List<Travel> travels;

    public TravelRepository() {
        this.travels = new ArrayList<>(Arrays.asList(
            new Travel("G3-1234", LocalDate.of(2025, 11, 10), 1200.50),
            new Travel("AD-4567", LocalDate.of(2025, 11, 12), 950.00),
            new Travel("TP-008", LocalDate.of(2025, 11, 10), 3400.75),
            new Travel("JA-1710", LocalDate.of(2025, 12, 25), 3400.75),
            new Travel("JK-6666", LocalDate.of(2026, 1, 15), 3400.75)
        ));
    }

    public List<Travel> getTravels() {
        return travels;
    }
}
