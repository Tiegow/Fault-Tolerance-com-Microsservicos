package com.imdtravel.TravelService.service.AirlinesHub;

import com.imdtravel.TravelService.model.AirlinesHubClient;
import com.imdtravel.TravelService.model.Transaction;
import com.imdtravel.TravelService.model.Travel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AirlinesHubClientWithFT implements AirlinesHubClient {
    @Override
    public Travel checkTravel(String flight, LocalDate day) {
        return new Travel("0", LocalDate.now(), 5.0);
    }

    @Override
    public Transaction sellTravel(String flight, LocalDate day) {
        return new Transaction("1");
    }
}
