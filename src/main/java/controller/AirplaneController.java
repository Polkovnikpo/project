package controller;

import dto.AirplaneDto;
import entity.Airplane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AirplaneService;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    private AirplaneService airplaneService;

    @Autowired
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    //принимаем dto и возвращаем тоже dto(не обязательно тот же - можно новый- смотря какая инфа нужна)
    // !!! смотри комментарии в сервисе
    @PostMapping
    public ResponseEntity<AirplaneDto> createAirplane(@RequestBody AirplaneDto dto) {
        AirplaneDto createAirplane = airplaneService.createAirplane(dto);
        return ResponseEntity.ok(createAirplane);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<AirplaneDto> updateAirplane(@PathVariable("id") Long id,@RequestBody AirplaneDto dto) {
        AirplaneDto updatedAirplane = airplaneService.updateAirplane(id,dto);
        return ResponseEntity.ok(updatedAirplane);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AirplaneDto> getAirplaneById(@PathVariable("id") Long id) {
        AirplaneDto airplaneById = airplaneService.getAirplaneById(id);
        return ResponseEntity.ok(airplaneById);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAirplaneById(@PathVariable("id") Long id) {
        airplaneService.deleteAirplaneById(id);
    }
}
