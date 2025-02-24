package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cashier")
public class CashierController {
    private final CashierService cashierService;

    public CashierController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping("/confirm/{ticketId}")
    @PreAuthorize("hasRole('CASHIER')")
    public ResponseEntity<TicketDto> confirmPurchase(@PathVariable Long ticketId) {
        TicketDto ticket = cashierService.confirmPurchase(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/cancel-booking/{ticketId}")
    @PreAuthorize("hasRole('CASHIER')")
    public ResponseEntity<TicketDto> cancelBooking(@PathVariable Long ticketId) {
        TicketDto ticket = cashierService.cancelBooking(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{flightId}/cancel")
    public ResponseEntity<String> cancelFlight(@PathVariable Long flightId) {
        String result = cashierService.cancelFlight(flightId);

        if (result.equals("Рейс успешно отменен")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
