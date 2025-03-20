package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.FlightDto;
import com.example.simbirsoft.entity.Airplane;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.repository.AirplaneRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.simbirsoft.repository.FlightRepository;

import java.awt.event.WindowFocusListener;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FlightService {

    private final static Logger log = LoggerFactory.getLogger(FlightService.class);
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
        log.info("Обновления полета с ID: {}, новые данные: {}", id, dto);
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Полет не найден"));

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
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Полет не найден"));

        FlightDto flightDto = mapFlightToDto(flight);
        log.info("Рейс с ID {} получен: {}", id, flightDto);
        return flightDto;
    }

    public void deleteFlightDto(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.NOT_FOUND, "Полет не найден");
        }
        flightRepository.deleteById(id);
        log.info("Рейс с ID {} успешно удален", id);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateFlightStatus() {
        LocalDateTime now = LocalDateTime.now();

        log.info("Начало обновления статуса рейсов на {}", now);

        flightRepository.updateStatusInProcess(now);

        flightRepository.updateStatusInCompleted(now);

        log.info("Обновление статусов рейсов завершено");
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
