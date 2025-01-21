package com.example.simbirsoft.service;


import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.entity.Airline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.simbirsoft.repository.AirlineRepository;

import java.util.Optional;

@Slf4j
@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Integer getTicketsCountByAirlineId(Long airlineId) {
        log.info("Получение количества билетов для авиакомпании с ID: {}", airlineId);
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> {
                    log.error("Авиакомпания с ID: {} не найдена", airlineId);
                    return new IllegalArgumentException("Авиакомпания не найдена");
                });

        int ticketCount = airline.getAirplanes().stream()
                .flatMap(airplane -> airplane.getFlights().stream())
                .flatMap(flight -> flight.getTickets().stream())
                .toList().size();

        log.info("Количество билетов для авиакомпании с ID: {}: {}", airlineId, ticketCount);
        return ticketCount;
    }

    public AirlineDto createAirline(AirlineDto dto) {
        log.info("Создание новой авиакомпании с данными", dto);
        Airline airline = mapDtoToAirline(dto);
        airlineRepository.save(airline);
        AirlineDto airlineDto = mapAirlineToDto(airline);
        log.info("Авиакомпания успешно создана: {}", airlineDto);
        return airlineDto;
    }

    public AirlineDto updateAirline(Long id, AirlineDto dto) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Авиакомпания с ID: {} не найдена", id);
                    return new IllegalArgumentException("Авиакомпания не найдена");
                });

        airline.setName(dto.getName());

        airlineRepository.save(airline);

        AirlineDto airlineDto = mapAirlineToDto(airline);
        log.info("Авиакомпания с ID {} успешно обновлена", id);
        return airlineDto;
    }

    public AirlineDto getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Авиакомпания с ID: {} не найдена", id);
                    return new IllegalArgumentException("Авиакомпания не найдена");
                });

        AirlineDto airlineDto = mapAirlineToDto(airline);
        log.info("Авиакомпания с ID {} успешно получена: {}", id, airlineDto);
        return airlineDto;
    }

    public void deleteAirlineById(Long id) {
        try {
            airlineRepository.deleteById(id);
            log.info("Авиакомпания с ID {} успешно удалена", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении авиакомпании с ID {}: {}", id, e.getMessage(), e);
        }
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
