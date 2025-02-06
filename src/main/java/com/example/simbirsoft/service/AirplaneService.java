package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.Airline;
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

    private final static Logger log = LoggerFactory.getLogger(AirlineService.class);
    private final AirplaneRepository airplaneRepository;
    private final AirlineRepository airlineRepository;

    public AirplaneService(AirplaneRepository airplaneRepository, AirlineRepository airlineRepository) {
        this.airplaneRepository = airplaneRepository;
        this.airlineRepository = airlineRepository;
    }

    public AirplaneDto createAirplane(AirplaneDto dto) {
        log.info("Создание нового самолета с данными: {}", dto);
        Airplane airplane = mapDtoToAirplane(dto);
        Airline airline = airlineRepository.findById(dto.getAirlineId()).orElseThrow();
        airplane.setAirline(airline);
        airplaneRepository.save(airplane);
        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        log.info("Самолет успешно создан");
        return airplaneDto;
    }


    public AirplaneDto updateAirplane(Long id, AirplaneDto dto) {
        log.info("Обновление самолета с ID: {}, новые данные: {}", id, dto);
        Optional<Airplane> airplaneOptional = airplaneRepository.findById(id);
        if (airplaneOptional.isPresent()) {
            Airplane airplane = airplaneOptional.get();
            airplane.setName(dto.getName());
            airplane.setModel(dto.getModel());
            airplane.setPlaces(dto.getPlaces());
            airplaneRepository.save(airplane);
            AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
            log.info("Самолет с ID {} успешно обновлен", id);
            return airplaneDto;
        } else {
            log.warn("Самолет с ID {} не найден для обновления", id);
            return null;
        }
    }

    public AirplaneDto getAirplaneById(Long id) {
        log.info("Получение самолета с ID: {}", id);
        Optional<Airplane> airplane = airplaneRepository.findById(id);
        if (airplane.isPresent()) {
            AirplaneDto airplaneDto = mapAirplaneToDto(airplane.get());
            log.info("Самолет с ID {} успешно получен: {}", id, airplaneDto);
            return airplaneDto;
        } else {
            log.warn("Самолет с ID {} не найден", id);
            return null;
        }
    }

    public void deleteAirplaneById(Long id) {
        log.info("Удаление самолета с ID: {}", id);
        try {
            airplaneRepository.deleteById(id);
            log.info("Самолет с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удаление самолета с ID {}: {}", id, e.getMessage(), e);
        }
    }

    public Airplane mapDtoToAirplane(AirplaneDto dto) {
        log.debug("Маппинг объекта AirplaneDto в Airplane: {}", dto);
        Airplane airplane = new Airplane();
        airplane.setName(dto.getName());
        airplane.setModel(dto.getModel());
        airplane.setPlaces(dto.getPlaces());
        return airplane;
    }

    public AirplaneDto mapAirplaneToDto(Airplane airplane) {
        log.debug("Маппинг объекта Airplane в AirplaneDto: {}", airplane);
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.setName(airplane.getName());
        airplaneDto.setModel(airplane.getModel());
        airplaneDto.setPlaces(airplane.getPlaces());
        return airplaneDto;
    }


}
