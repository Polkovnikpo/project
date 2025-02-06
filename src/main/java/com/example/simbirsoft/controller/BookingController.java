package com.example.simbirsoft.controller;

import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bayer")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestParam String startingPoint, @RequestParam Long userId){
        Ticket BookedTicket = bookingService.bookTicket(startingPoint,userId);
        return ResponseEntity.ok(BookedTicket);
    }
}
