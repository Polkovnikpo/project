package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/airline")
public class AirlineController {
    public AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<AirlineDto> createAirline(@RequestBody AirlineDto dto) {
        AirlineDto createdAirplane = airlineService.createAirline(dto);
        return ResponseEntity.ok(createdAirplane);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<AirlineDto> updateAirline(@PathVariable("id") Long id, @RequestBody AirlineDto dto) {
        AirlineDto updatedAirline = airlineService.updateAirline(id, dto);
        return ResponseEntity.ok(updatedAirline);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<AirlineDto> getAirlineById(@PathVariable("id") Long id) {
        AirlineDto airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteAirline(@PathVariable("id") Long id) {
        airlineService.deleteAirlineById(id);
    }


    @GetMapping("/tickets/count/{airlineId}")
    public ResponseEntity<Integer> getTicketCountByAirlineId(@PathVariable Long airlineId) {
        Integer ticketsCount = airlineService.getTicketsCountByAirlineId(airlineId);
        return ResponseEntity.ok(ticketsCount);
    }
}
