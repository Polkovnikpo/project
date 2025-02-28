package com.example.simbirsoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация об авиакомпании")
public class AirlineDto {
    @Schema(description = "Название авиакомпании", example = "Pobeda")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
