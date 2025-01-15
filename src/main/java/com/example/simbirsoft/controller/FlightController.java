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

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto dto) {
        FlightDto createFlight = flightService.createFlight(dto);
        return ResponseEntity.ok(createFlight);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable("id") Long id, @RequestBody FlightDto dto) {
        FlightDto updateFlight = flightService.updateFlight(id, dto);
        return ResponseEntity.ok(updateFlight);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id") Long id) {
        FlightDto getFlight = flightService.getFlightById(id);
        return ResponseEntity.ok(getFlight);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteFlightBeId(@PathVariable("id") Long id) {
        flightService.deleteFlightDto(id);
    }
}
