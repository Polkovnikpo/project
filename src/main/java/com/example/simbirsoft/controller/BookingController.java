package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{ticketId}/book")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity<TicketDto> bookTicket(@PathVariable Long ticketId){
        TicketDto bookedTicket = bookingService.bookTicket(ticketId);
        return ResponseEntity.ok(bookedTicket);
    }
}
