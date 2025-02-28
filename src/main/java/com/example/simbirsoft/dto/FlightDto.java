package com.example.simbirsoft.dto;

import com.example.simbirsoft.entity.FlightStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Информация о рейсе")
public class FlightDto {
    @Schema(description = "Начальная точка рейса", example = "Казань")
    private String startingPoint;

    @Schema(description = "Конечная точка рейса", example = "Москва")
    private String destinationPoint;

    @Schema(description = "Время отправления рейса", example = "2025-02-28T10:30:00")
    private LocalDateTime departureTime;

    @Schema(description = "Время прибытия рейса", example = "2025-02-28T12:30:00")
    private LocalDateTime arrivalTime;

    @Schema(description = "Статус рейса", example = "IN_PROCESS")
    private FlightStatus status;

    @Schema(description = "Идентификатор самолета, назначенного на рейс", example = "123")
    private long airplaneId;

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

    public long getAirplaneId() {
        return airplaneId;
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

    public void setAirplaneId(long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}
