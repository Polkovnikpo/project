package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
@Tag(name = "Бронирование билетов", description = "Методы для бронирования билетов покупателями")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{ticketId}/book")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    @Operation(summary = "Забронировать билет", description = "Позволяет покупателю забронировать билет по его ID")
    @ApiResponse(responseCode = "200", description = "Билет успешно забронирован")
    @ApiResponse(responseCode = "404", description = "Билет не найден")
    @ApiResponse(responseCode = "409", description = "Билет уже забронирован или куплен")
public ResponseEntity<TicketDto> bookTicket(@PathVariable Long ticketId, @RequestBody(required = false) String promoCode){
        TicketDto bookedTicket = bookingService.bookTicket(ticketId, promoCode);
        return ResponseEntity.ok(bookedTicket);
    }
}
