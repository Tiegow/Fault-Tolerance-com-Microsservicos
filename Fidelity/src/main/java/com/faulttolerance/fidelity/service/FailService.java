package com.faulttolerance.fidelity.service;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FailService {

    private final ApplicationContext applicationContext;

    public FailService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void createChanceFailCrash(double chance) {
        if (Math.random() < chance) {
            int exitCode = SpringApplication.exit(applicationContext, () -> 0);
            System.exit(exitCode);
        }
    }
}

