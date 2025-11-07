package com.airlineshub.AirlinesHub.model;

import java.time.LocalDate;

public class Travel {
    private String flight;
    private LocalDate day;
    private Double value;

    public Travel() {}

    public Travel(String flight, LocalDate day, Double value) {
        this.flight = flight;
        this.day = day;
        this.value = value;
    }    

    public String getFlight() {
        return flight;
    }
    public void setFlight(String flight) {
        this.flight = flight;
    }
    public LocalDate getDay() {
        return day;
    }
    public void setDay(LocalDate day) {
        this.day = day;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
}
