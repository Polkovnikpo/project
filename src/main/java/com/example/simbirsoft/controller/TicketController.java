package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.simbirsoft.service.TicketService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketRepository ticketRepository;
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto dto) {
        TicketDto createTicket = ticketService.createTicket(dto);
        return ResponseEntity.ok(createTicket);
    }

    @PostMapping(value = "/commission")
    ResponseEntity<TicketDto> createTicketWithCommission(@RequestBody TicketDto dto) {
        TicketDto ticketWithCommission = ticketService.createTicketWithCommission(dto);
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
    public ResponseEntity<Integer> getTicketCountByStartingPoint(@RequestParam("startingPoint") String startingPoint) {
        int ticketCount = ticketService.getTicketCountByStartingPoint(startingPoint);
        return ResponseEntity.ok(ticketCount);
    }

    @GetMapping(value = "/averageCommissionInRubles")
    public ResponseEntity<BigDecimal> getAverageCommissionInRubles() {
        BigDecimal commission = ticketService.getAverageCommissionInRubles();
        return ResponseEntity.ok(commission);
    }

    @GetMapping("/showSold")
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam(value = "showSold", defaultValue = "false") boolean showSold) {
        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> filterTickets = ticketService.getAllTickets(allTickets, showSold);
        return ResponseEntity.ok(filterTickets);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicketById(id);
    }
}

