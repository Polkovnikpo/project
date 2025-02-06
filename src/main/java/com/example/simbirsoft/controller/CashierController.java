package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cashier")
public class CashierController {
    private final CashierService cashierService;

    @Autowired
    public CashierController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping("/confirm/{ticketId}")
    @PreAuthorize("hasRole('CASHIER')")
    public ResponseEntity<Ticket> confirmPurchase(@PathVariable Long ticketId, @PathVariable Long cashierId) {
        Ticket ticket = cashierService.confirmPurchase(ticketId, cashierId);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/cancel-booking/{ticketId}")
    @PreAuthorize("hasRole('CASHIER')")
    public ResponseEntity<Ticket> cancelBooking(@PathVariable Long ticketId, @PathVariable Long cashierId) {
        Ticket ticket = cashierService.cancelBooking(ticketId, cashierId);
        return ResponseEntity.ok(ticket);
    }
}
