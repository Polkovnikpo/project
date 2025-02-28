package com.example.simbirsoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о самолете")
public class AirplaneDto {
    @Schema(description = "Название самолета", example = "Boeing 300")
    private String name;

    @Schema(description = "Модель самолета", example = "300-1")
    private String model;

    @Schema(description = "Количество мест", example = "200")
    private Integer places;

    @Schema(description = "Идентификатор авиакомпании, к которой принадлежит самолет", example = "123")
    private long airlineId;

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public Integer getPlaces() {
        return places;
    }

    public long getAirlineId() {
        return airlineId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public void setAirlineId(long airlineId) {
        this.airlineId = airlineId;
    }
}
