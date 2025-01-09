package controller;

import dto.TicketDto;
import entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TicketService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto dto) {
        TicketDto ticketDto = ticketService.createTicket(dto);
        return ResponseEntity.ok(ticketDto);
    }

    @PostMapping("/commission")
    ResponseEntity<TicketDto> createTicketWithCommission(@RequestBody TicketDto dto, @RequestParam BigDecimal commission) {
        TicketDto ticketDto = ticketService.createTicketWithCommission(dto, commission);
        return ResponseEntity.ok(ticketDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable("id") Long id, @RequestBody TicketDto dto) {
        TicketDto ticketDto = ticketService.updateTicketById(id, dto);
        return ResponseEntity.ok(ticketDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("id") Long id) {
        TicketDto ticketDto = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticketDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicketById(id);
    }
}
