package com.example.simbirsoft.service;


import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Airline;
import com.example.simbirsoft.entity.Airplane;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.Ticket;
import org.springframework.stereotype.Service;
import com.example.simbirsoft.repository.AirlineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public AirlineDto mapAirlineToDto(Airline airline) {
        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setName(airline.getName());
        return airlineDto;
    }

    public Airline mapDtoToAirline(AirlineDto airlineDto) {
        Airline airline = new Airline();
        airline.setName(airlineDto.getName());
        return airline;
    }


    public Integer getTicketsCountByAirlineId(Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow();

        return airline.getAirplanes().stream()
                .flatMap(airplane -> airplane.getFlights().stream())
                .flatMap(flight -> flight.getTickets().stream())
                .toList().size();
    }
}
