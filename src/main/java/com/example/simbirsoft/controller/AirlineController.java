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

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<AirlineDto> createAirline(@RequestBody AirlineDto dto) {
        AirlineDto createAirplane = airlineService.createAirline(dto);
        return ResponseEntity.ok(createAirplane);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<AirlineDto> updateAirline(@PathVariable("id") Long id, @RequestBody AirlineDto dto) {
        AirlineDto updateAirline = airlineService.updateAirline(id, dto);
        return ResponseEntity.ok(updateAirline);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<AirlineDto> getAirlineById(@PathVariable("id") Long id) {
        AirlineDto getAirline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(getAirline);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteAirline(@PathVariable("id") Long id) {
        airlineService.deleteAirlineById(id);
    }


    @GetMapping("/tickets/count/{airlineId}")
    public ResponseEntity<Integer> getTicketCountByAirlineId(@PathVariable Long airlineId) {
        Integer ticketCount = airlineService.getTicketsCountByAirlineId(airlineId);
        return ResponseEntity.ok(ticketCount);
    }
}
