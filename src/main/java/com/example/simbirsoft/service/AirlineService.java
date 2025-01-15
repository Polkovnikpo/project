package com.example.simbirsoft.service;


import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.dto.FlightDto;
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


    public Integer getTicketsCountByAirlineId(Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow();

        return airline.getAirplanes().stream()
                .flatMap(airplane -> airplane.getFlights().stream())
                .flatMap(flight -> flight.getTickets().stream())
                .toList().size();
    }

    public AirlineDto createAirline(AirlineDto dto) {
        Airline airline = mapDtoToAirline(dto);
        airlineRepository.save(airline);
        AirlineDto airlineDto = mapAirlineToDto(airline);
        return airlineDto;
    }

    public AirlineDto updateAirline(Long id, AirlineDto dto) {
        Optional<Airline> airlineOptional = airlineRepository.findById(id);
        if (airlineOptional.isPresent()) {
            Airline airline = airlineOptional.get();
            airline.setName(dto.getName());

            airlineRepository.save(airline);

            AirlineDto airlineDto = mapAirlineToDto(airline);
            return airlineDto;
        }
        return null;
    }

    public AirlineDto getAirlineById(Long id) {
        Optional<Airline> airlineOptional = airlineRepository.findById(id);
        if (airlineOptional.isPresent()) {
            AirlineDto airlineDto = mapAirlineToDto(airlineOptional.get());
            return airlineDto;
        }
        return null;
    }

    public void deleteAirlineById(Long id){
        airlineRepository.deleteById(id);
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


}
