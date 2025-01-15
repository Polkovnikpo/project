package com.example.simbirsoft.dto;

import lombok.Data;

@Data
public class AirplaneDto {
    private String name;
    private String model;
    private Integer places;
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
