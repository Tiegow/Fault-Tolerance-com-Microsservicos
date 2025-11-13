package com.imdtravel.TravelService.controller;

import java.time.LocalDate;

import com.imdtravel.TravelService.model.Transaction;
import com.imdtravel.TravelService.service.IMDTravelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imdtravel")
public class IMDTravelController {

    private final IMDTravelService imdtravelService;

    public IMDTravelController(IMDTravelService imdtravelService) {
        this.imdtravelService = imdtravelService;
    }

    @PostMapping("/buyTicket")
    public ResponseEntity<Transaction> buyTicket(@RequestParam String flight, @RequestParam LocalDate day,
                                                 @RequestParam String user, @RequestParam Boolean ft) {

        Transaction transaction = imdtravelService.buyTicket(flight, day, user, ft);
 
        return ResponseEntity.ok(transaction);
    }
}
