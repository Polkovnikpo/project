package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.Airline;
import com.example.simbirsoft.repository.AirlineRepository;
import com.example.simbirsoft.repository.AirplaneRepository;
import com.example.simbirsoft.dto.AirplaneDto;
import com.example.simbirsoft.entity.Airplane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirlineRepository airlineRepository;

    public AirplaneService(AirplaneRepository airplaneRepository, AirlineRepository airlineRepository) {
        this.airplaneRepository = airplaneRepository;
        this.airlineRepository = airlineRepository;
    }

    public AirplaneDto createAirplane(AirplaneDto dto) {
        Airplane airplane = mapDtoToAirplane(dto);
        Airline airline = airlineRepository.findById(dto.getAirlineId()).orElseThrow();
        airplane.setAirline(airline);
        airplaneRepository.save(airplane);
        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет успешно создан");
        return airplaneDto;
    }


    public AirplaneDto updateAirplane(Long id, AirplaneDto dto) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Самолет с ID: {} не найден", id);
                    return new IllegalArgumentException("Самолет не найден");
                });

        airplane.setName(dto.getName());
        airplane.setModel(dto.getModel());
        airplane.setPlaces(dto.getPlaces());

        airplaneRepository.save(airplane);

        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет с ID {} успешно обновлен", id);
        return airplaneDto;
    }

    public AirplaneDto getAirplaneById(Long id) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Самолет с ID: {} не найден", id);
                    return new IllegalArgumentException("Самолет не найден");
                });
        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет с ID {} успешно получен: {}", id, airplaneDto);
        return airplaneDto;
    }

    public void deleteAirplaneById(Long id) {
        try {
            airplaneRepository.deleteById(id);
            log.info("Самолет с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удаление самолета с ID {}: {}", id, e.getMessage(), e);
        }
    }

    public Airplane mapDtoToAirplane(AirplaneDto dto) {
        Airplane airplane = new Airplane();
        airplane.setName(dto.getName());
        airplane.setModel(dto.getModel());
        airplane.setPlaces(dto.getPlaces());
        return airplane;
    }

    public AirplaneDto mapAirplaneToDto(Airplane airplane) {
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.setName(airplane.getName());
        airplaneDto.setModel(airplane.getModel());
        airplaneDto.setPlaces(airplane.getPlaces());
        return airplaneDto;
    }


}
