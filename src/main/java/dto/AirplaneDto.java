package dto;

import lombok.Data;

@Data
public class AirplaneDto {
    private Long id;
    private String name;
    private String model;
    private Integer places;
    private AirlineDto airlineDto;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAirlineDto(AirlineDto airlineDto) {
        this.airlineDto = airlineDto;
    }

    public AirlineDto getAirlineDto() {
        return airlineDto;
    }
}
