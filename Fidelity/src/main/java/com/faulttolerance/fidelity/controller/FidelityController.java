package com.faulttolerance.fidelity.controller;

import com.faulttolerance.fidelity.model.FidelityBonus;
import com.faulttolerance.fidelity.service.FailService;
import com.faulttolerance.fidelity.service.FidelityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bonus")
public class FidelityController {

    private final FidelityService fidelityService;
    private final FailService failService;

    public FidelityController(FidelityService fidelityService, FailService failService) {
        this.fidelityService = fidelityService;
        this.failService = failService;
    }

    @PostMapping
    public ResponseEntity<Void> insertBonus(@ModelAttribute FidelityBonus bonus) {
        failService.createChanceFailCrash(0.02);

        fidelityService.createBonus(bonus);

        return ResponseEntity.ok().build();
    }
}
