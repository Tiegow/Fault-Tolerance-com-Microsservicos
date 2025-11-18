package com.imdtravel.TravelService.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(Exception exception, HttpServletRequest request) {

        StandardError err = new StandardError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getServletPath()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<StandardError> handleExternalServiceException(Exception exception, HttpServletRequest request) {

        StandardError err = new StandardError(
                Instant.now(),
                HttpStatus.BAD_GATEWAY.value(),
                exception.getMessage(),
                request.getServletPath()
        );

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
    }

    @ExceptionHandler(UnavaliableServiceException.class)
    public ResponseEntity<StandardError> handleUnavaliableServiceException(Exception exception, HttpServletRequest request) {
        
        StandardError err = new StandardError(
                Instant.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                exception.getMessage(),
                request.getServletPath()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
    }
}