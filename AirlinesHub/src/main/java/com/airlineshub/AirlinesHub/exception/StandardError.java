package com.airlineshub.AirlinesHub.exception;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String path
        ) {
}
