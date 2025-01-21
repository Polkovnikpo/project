package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.CreateTicketDto;
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

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CreateTicketDto> createTicket(@RequestBody CreateTicketDto createTicketDto) {
            CreateTicketDto createdTicket =  ticketService.createTicket(createTicketDto);
            return ResponseEntity.ok(createdTicket);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable("id") Long id, @RequestBody TicketDto dto) {
        TicketDto updatedTicket = ticketService.updateTicketById(id, dto);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("id") Long id) {
        TicketDto ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
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

