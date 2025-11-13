package com.imdtravel.TravelService.model;

import java.time.LocalDate;

public interface AirlinesHubClient {
    Travel checkTravel(String flight, LocalDate day);
    Transaction sellTravel(String flight, LocalDate day);
}
