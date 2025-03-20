package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.Airline;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.repository.AirlineRepository;
import com.example.simbirsoft.repository.AirplaneRepository;
import com.example.simbirsoft.dto.AirplaneDto;
import com.example.simbirsoft.entity.Airplane;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirplaneService {

    private final static Logger log = LoggerFactory.getLogger(AirplaneService.class);
    private final AirplaneRepository airplaneRepository;
    private final AirlineRepository airlineRepository;

    public AirplaneService(AirplaneRepository airplaneRepository, AirlineRepository airlineRepository) {
        this.airplaneRepository = airplaneRepository;
        this.airlineRepository = airlineRepository;
    }

    public AirplaneDto createAirplane(AirplaneDto dto) {
        log.info("Создание нового самолета с данными: {}", dto);

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Авиакомпания не найдена"));

        Airplane airplane = mapDtoToAirplane(dto);
        airplane.setAirline(airline);
        airplaneRepository.save(airplane);

        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет успешно создан: {}", airplaneDto);
        return airplaneDto;
    }


    public AirplaneDto updateAirplane(Long id, AirplaneDto dto) {
        log.info("Обновление самолета с ID: {}, новые данные: {}", id, dto);
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Самолет не найден"));

        airplane.setName(dto.getName());
        airplane.setModel(dto.getModel());
        airplane.setPlaces(dto.getPlaces());
        airplaneRepository.save(airplane);

        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет с ID {} успешно обновлен", id);
        return airplaneDto;
    }

    public AirplaneDto getAirplaneById(Long id) {
        log.info("Получение самолета с ID: {}", id);
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Самолет не найден"));

        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет с ID {} успешно получен: {}", id, airplaneDto);
        return airplaneDto;
    }

    public void deleteAirplaneById(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.NOT_FOUND, "Самолет не найден");
        }
        airplaneRepository.deleteById(id);
        log.info("Самолет с ID {} успешно удален", id);
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
