package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.AirplaneDto;
import com.example.simbirsoft.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    private AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping
    public ResponseEntity<AirplaneDto> createAirplane(@RequestBody AirplaneDto dto) {
        AirplaneDto createdAirplane = airplaneService.createAirplane(dto);
        return ResponseEntity.ok(createdAirplane);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<AirplaneDto> updateAirplane(@PathVariable("id") Long id,@RequestBody AirplaneDto dto) {
        AirplaneDto updatedAirplane = airplaneService.updateAirplane(id,dto);
        return ResponseEntity.ok(updatedAirplane);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AirplaneDto> getAirplaneById(@PathVariable("id") Long id) {
        AirplaneDto airplane = airplaneService.getAirplaneById(id);
        return ResponseEntity.ok(airplane);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAirplaneById(@PathVariable("id") Long id) {
        airplaneService.deleteAirplaneById(id);
    }
}
