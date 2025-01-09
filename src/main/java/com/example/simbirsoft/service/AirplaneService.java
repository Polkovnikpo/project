package com.example.simbirsoft.service;

import com.example.simbirsoft.repository.AirplaneRepository;
import com.example.simbirsoft.dto.AirplaneDto;
import com.example.simbirsoft.entity.Airplane;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public AirplaneDto createAirplane(AirplaneDto dto) {
        //с помощью метода превратили dto в сущность
        Airplane airplane = mapDtoToAirplane(dto);
        // сохранили
        airplaneRepository.save(airplane);
        //обратно из сущности первращаем в dto
        // тут на самом деле можно было бы вернуть ту же dto
        // например return dto(которая в парметрах) вместо 28 и 29 строчки -
        // то есть не создавая новую - так как хотим вернуть тоже самое
        AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
        return airplaneDto;
    }


    public AirplaneDto updateAirplane(Long id, AirplaneDto dto) {
        Optional<Airplane> airplaneOptional = airplaneRepository.findById(id);
        // достаем опшионал из б
        //проверяем есть ли такой самолет
        if (airplaneOptional.isPresent()) {
            //достаем самолет сам
            Airplane airplane = airplaneOptional.get();
            //устанавливаем поля нужные
            airplane.setName(dto.getName());
            airplane.setModel(dto.getModel());
            airplane.setPlaces(dto.getPlaces());

            //сохраняем
            airplaneRepository.save(airplane);

            //превращаем в dto чтобы вернуть результат
            //тут тоже можно было бы просто вернуть тот же dto из параметров
            AirplaneDto airplaneDto = mapAirplaneToDto(airplane);
            return airplaneDto;
        }
        return null;
    }

    public AirplaneDto getAirplaneById(Long id) {
        Optional<Airplane> airplane = airplaneRepository.findById(id);
        if (airplane.isPresent()) {
            AirplaneDto airplaneDto = mapAirplaneToDto(airplane.get());
            return airplaneDto;
        }
        return null;
    }

    public void deleteAirplaneById(Long id) {
        airplaneRepository.deleteById(id);
    }

    //метод превращения dto в энтити самолет
    public Airplane mapDtoToAirplane(AirplaneDto dto) {
        Airplane airplane = new Airplane();
        airplane.setName(dto.getName());
        airplane.setModel(dto.getModel());
        airplane.setPlaces(dto.getPlaces());
        return airplane;
    }
    //метод превращения энтити самолет в dto
    public AirplaneDto mapAirplaneToDto(Airplane airplane) {
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.setName(airplane.getName());
        airplaneDto.setModel(airplane.getModel());
        airplaneDto.setPlaces(airplane.getPlaces());
        return airplaneDto;
    }


}
