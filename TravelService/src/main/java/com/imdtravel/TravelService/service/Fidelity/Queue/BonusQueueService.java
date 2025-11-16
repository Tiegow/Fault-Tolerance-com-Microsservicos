package com.imdtravel.TravelService.service.Fidelity.Queue;

import com.imdtravel.TravelService.model.FidelityBonus;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class BonusQueueService {

    private final Queue<FidelityBonus> queue = new ConcurrentLinkedQueue<>();

    public void add(FidelityBonus bonus) {
        queue.add(bonus);
    }

    public FidelityBonus poll() {
        return queue.poll();
    }
}
