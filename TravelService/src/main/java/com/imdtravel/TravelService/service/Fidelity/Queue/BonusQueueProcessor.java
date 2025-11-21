package com.imdtravel.TravelService.service.Fidelity.Queue;

import com.imdtravel.TravelService.model.FidelityBonus;
import com.imdtravel.TravelService.service.Fidelity.FidelityClientWithFT;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BonusQueueProcessor {

    private final BonusQueueService bonusQueueService;
    private final FidelityClientWithFT fidelityClientWithFT;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public BonusQueueProcessor(BonusQueueService bonusQueueService, FidelityClientWithFT fidelityClientWithFT,
                               CircuitBreakerRegistry circuitBreakerRegistry) {
        this.bonusQueueService = bonusQueueService;
        this.fidelityClientWithFT = fidelityClientWithFT;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Scheduled(fixedDelay = 2000)
    public void process() {
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("createBonusBreaker");

        if (cb.getState() == CircuitBreaker.State.OPEN) {
            return;
        }

        FidelityBonus task = bonusQueueService.poll();

        if (task == null) {
            return;
        }

        fidelityClientWithFT.createBonus(task);
    }
}
