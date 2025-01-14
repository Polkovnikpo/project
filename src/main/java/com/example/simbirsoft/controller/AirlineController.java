package com.example.simbirsoft.controller;

import com.example.simbirsoft.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/airline")
public class AirlineController {
    public AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping("/tickets/count/{airlineId}")
    public ResponseEntity<Integer> getTicketCountByAirlineId(@PathVariable Long airlineId){
        Integer ticketCount = airlineService.getTicketsCountByAirlineId(airlineId);
        return ResponseEntity.ok(ticketCount);
    }
}
