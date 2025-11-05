package com.faulttolerance.fidelity.controller;

import com.faulttolerance.fidelity.model.FidelityBonus;
import com.faulttolerance.fidelity.service.FidelityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bonus")
public class FidelityController {

    private final FidelityService service;

    public FidelityController(FidelityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> insertBonus(@ModelAttribute FidelityBonus bonus) {
        service.createBonus(bonus);

        return ResponseEntity.ok().build();
    }
}
