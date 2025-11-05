package com.airlineshub.AirlinesHub.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FailService {
    private AtomicBoolean isSlow = new AtomicBoolean(false);
    private AtomicLong slowStateEndTime = new AtomicLong();

    public void createChanceFailOmission(Double chance) {
        if (Math.random() < chance) {
            while (true) {}
        }
    }

    public void createChanceFailTime(Integer secondsDelay, Double chance, Integer secondsFailDuration) {
        long currTime = System.currentTimeMillis();

        if (isSlow.get()) {
            if (currTime < slowStateEndTime.get()) {
                try {
                    Thread.sleep(secondsDelay * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                isSlow.set(false);
            }
        }

        if (Math.random() < chance && !isSlow.get()) {
            isSlow.set(true);
            slowStateEndTime.set(currTime + secondsFailDuration * 1000);

            try {
                Thread.sleep(secondsDelay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
