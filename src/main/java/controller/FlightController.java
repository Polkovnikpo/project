package controller;

import dto.FlightDto;
import entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FlightService;

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
        FlightDto flightDto = flightService.createFlight(dto);
        return ResponseEntity.ok(flightDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable("id") Long id, @RequestBody FlightDto dto) {
        FlightDto flightDto = flightService.updateFlight(id, dto);
        return ResponseEntity.ok(flightDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id") Long id) {
        FlightDto flightDto = flightService.getFlightById(id);
        return ResponseEntity.ok(flightDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteFlightBeId(@PathVariable("id") Long id) {
        flightService.deleteFlightDto(id);
    }
}
