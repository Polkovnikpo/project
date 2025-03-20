package com.example.simbirsoft.service;


import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.dto.FlightDto;
import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Airline;
import com.example.simbirsoft.entity.Airplane;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.simbirsoft.repository.AirlineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {

    private final static Logger log = LoggerFactory.getLogger(AirlineService.class);
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Integer getTicketsCountByAirlineId(Long airlineId) {
        log.info("Получение количества билетов для авиакомпании с ID: {}", airlineId);

        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Авиакомпания не найдена"));

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
        log.info("Обновление авиакомпании с ID: {}, новые данные: {}", id, dto);

        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Авиакомпания не найдена"));

        airline.setName(dto.getName());
        airlineRepository.save(airline);

        AirlineDto airlineDto = mapAirlineToDto(airline);
        log.info("Авиакомпания с ID {} успешно обновлена", id);
        return airlineDto;
    }

    public AirlineDto getAirlineById(Long id) {
        log.info("Получение авиакомпании с ID: {}", id);
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Авиакомпания не найдена"));

        AirlineDto airlineDto = mapAirlineToDto(airline);
        log.info("Авиакомпания с ID {} успешно получена: {}", id, airlineDto);
        return airlineDto;
    }

    public void deleteAirlineById(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.NOT_FOUND, "Авиакомпания не найдена");
        }
        airlineRepository.deleteById(id);
        log.info("Авиакомпания с ID {} успешно удалена", id);
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
