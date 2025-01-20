package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.simbirsoft.service.TicketService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto dto) {
        TicketDto createTicket = ticketService.createTicket(dto);
        return ResponseEntity.ok(createTicket);
    }

    @PostMapping(value = "/commission")
    ResponseEntity<TicketDto> createTicketWithCommission(@RequestBody TicketDto dto, @RequestParam BigDecimal commission) {
        TicketDto ticketWithCommission = ticketService.createTicketWithCommission(dto, commission);
        return ResponseEntity.ok(ticketWithCommission);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable("id") Long id, @RequestBody TicketDto dto) {
        TicketDto updateTicket = ticketService.updateTicketById(id, dto);
        return ResponseEntity.ok(updateTicket);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("id") Long id) {
        TicketDto getTicket = ticketService.getTicketById(id);
        return ResponseEntity.ok(getTicket);
    }

    @GetMapping(value = "/ticketCountByStartingPoint")
    public ResponseEntity<Integer> getTicketCountByStartingPoint(@RequestParam("startingPoint") String startingPoint){
        int ticketCount = ticketService.getTicketCountByStartingPoint(startingPoint);
        return ResponseEntity.ok(ticketCount);
    }

    @GetMapping(value = "/averageCommissionInRubles")
    public ResponseEntity<BigDecimal> getAverageCommissionInRubles(){
        BigDecimal commission = ticketService.getAverageCommissionInRubles();
        return ResponseEntity.ok(commission);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicketById(id);
    }
}

