package com.example.simbirsoft.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightDto {
    private String startingPoint;
    private String destinationPoint;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
