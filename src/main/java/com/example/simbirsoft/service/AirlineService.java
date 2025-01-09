package com.example.simbirsoft.service;


import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.entity.Airline;
import org.springframework.stereotype.Service;
import com.example.simbirsoft.repository.AirlineRepository;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public AirlineDto mapAirlineToDto(Airline airline){
        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setName(airline.getName());
        return airlineDto;
    }

    public Airline mapDtoToAirline(AirlineDto airlineDto){
        Airline airline = new Airline();
        airline.setName(airlineDto.getName());
        return airline;
    }
}
