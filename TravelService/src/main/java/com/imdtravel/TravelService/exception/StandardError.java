package com.imdtravel.TravelService.exception;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String path
        ) {
}
