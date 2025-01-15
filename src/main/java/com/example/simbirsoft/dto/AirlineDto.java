package com.example.simbirsoft.dto;

import lombok.Data;

@Data
public class AirlineDto {
    private String name;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
