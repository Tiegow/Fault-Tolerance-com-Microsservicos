package com.imdtravel.TravelService.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imdtravel.TravelService.dto.FidelityBonusDTO;
import com.imdtravel.TravelService.dto.VooDTO;
import com.imdtravel.TravelService.service.AirlinesHubClient;
import com.imdtravel.TravelService.service.ExchangeClient;
import com.imdtravel.TravelService.service.FidelityClient;

@RestController
@RequestMapping("/imdtravel")
public class IMDTravelController {

    @Autowired
    private AirlinesHubClient airlinesHubClient;

    @Autowired
    private ExchangeClient exchangeClient;

    @Autowired
    private FidelityClient fidelityClient;
    
    @PostMapping("buyTicket")
    public ResponseEntity<String> comprarTicket(@RequestParam String flight, @RequestParam LocalDate day, @RequestParam String user) {

        //Req. 1:
        VooDTO voo = airlinesHubClient.consultarVoo(flight, day);
        if (voo == null) {
            return ResponseEntity.notFound().build();
        }

        //Req. 2:
        Double taxa = exchangeClient.converter();

        //Req. 3:
        String idCompra = airlinesHubClient.venderVoo(voo.getFlight(), voo.getDay());

        //Req. 4:
        int bonus = (int) Math.round(voo.getValue()); 
        fidelityClient.createBonus(new FidelityBonusDTO(user, bonus));
 
        return ResponseEntity.ok(idCompra);
    }
}
