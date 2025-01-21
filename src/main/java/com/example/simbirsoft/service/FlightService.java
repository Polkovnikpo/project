package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.FlightDto;
import com.example.simbirsoft.entity.Airplane;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.repository.AirplaneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.simbirsoft.repository.FlightRepository;

import java.util.Optional;

@Slf4j
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;

    public FlightService(FlightRepository flightRepository, AirplaneRepository airplaneRepository) {
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
    }

    public FlightDto createFlight(FlightDto dto) {
        Flight flight = mapDtoToFlight(dto);
        Airplane airplane = airplaneRepository.findById(dto.getAirplaneId()).orElseThrow();
        flight.setAirplane(airplane);
        flightRepository.save(flight);
        FlightDto flightDto = mapFlightToDto(flight);
        log.info("Рейс успешно создан");
        return flightDto;
    }

    public FlightDto updateFlight(Long id, FlightDto dto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Рейс с ID: {} не найден", id);
                    return new IllegalArgumentException("Рейс не найден");
                });

        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setStartingPoint(dto.getStartingPoint());
        flight.setDestinationPoint(dto.getDestinationPoint());

        flightRepository.save(flight);

        FlightDto flightDto = mapFlightToDto(flight);
        log.info("Рейс с ID {} успешно обновлен", id);
        return flightDto;
    }

    public FlightDto getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Рeйс с ID {} не найден", id);
                    return new IllegalArgumentException("Руйс не найден");
                });

        FlightDto flightDto = mapFlightToDto(flight);

        log.info("Рейс с ID {} получен: {}", id, flightDto);
        return flightDto;
    }

    public void deleteFlightDto(Long id) {
        try {
            flightRepository.deleteById(id);
            log.info("Рейс с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удаление рейса с ID {}: {}", id, e.getMessage(), e);
        }
    }

    public Flight mapDtoToFlight(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setStartingPoint(flightDto.getStartingPoint());
        flight.setDestinationPoint(flightDto.getDestinationPoint());
        return flight;
    }

    public FlightDto mapFlightToDto(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.setArrivalTime(flight.getArrivalTime());
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setStartingPoint(flight.getStartingPoint());
        flightDto.setDestinationPoint(flight.getDestinationPoint());
        return flightDto;
    }
}
