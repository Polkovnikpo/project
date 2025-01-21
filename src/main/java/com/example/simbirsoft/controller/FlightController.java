package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.FlightDto;
import com.example.simbirsoft.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto dto) {
        FlightDto createdFlight = flightService.createFlight(dto);
        return ResponseEntity.ok(createdFlight);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable("id") Long id, @RequestBody FlightDto dto) {
        FlightDto updatedFlight = flightService.updateFlight(id, dto);
        return ResponseEntity.ok(updatedFlight);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id") Long id) {
        FlightDto flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteFlightBeId(@PathVariable("id") Long id) {
        flightService.deleteFlightDto(id);
    }
}
