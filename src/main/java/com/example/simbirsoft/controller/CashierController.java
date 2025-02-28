package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.service.CashierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cashier")
@Tag(name = "Кассир", description = "Методы управления билетами и рейсами для кассира")
public class CashierController {
    private final CashierService cashierService;

    public CashierController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping("/confirm/{ticketId}")
    @PreAuthorize("hasRole('CASHIER')")
    @Operation(summary = "Подтвердить покупку билета", description = "Кассир подтверждает покупку билета по ID")
    @ApiResponse(responseCode = "200", description = "Покупка успешно подтверждена")
    @ApiResponse(responseCode = "404", description = "Билет не найден")
    public ResponseEntity<TicketDto> confirmPurchase(@PathVariable Long ticketId) {
        try {
            TicketDto ticket = cashierService.confirmPurchase(ticketId);
            return ResponseEntity.ok(ticket);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/cancel-booking/{ticketId}")
    @PreAuthorize("hasRole('CASHIER')")
    @Operation(summary = "Отменить бронь билета", description = "Кассир отменяет бронь билета по ID")
    @ApiResponse(responseCode = "200", description = "Бронь успешно отменена")
    @ApiResponse(responseCode = "404", description = "Билет не найден")
    public ResponseEntity<TicketDto> cancelBooking(@PathVariable Long ticketId) {
        try {
            TicketDto ticket = cashierService.cancelBooking(ticketId);
            return ResponseEntity.ok(ticket);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{flightId}/cancel")
    @Operation(summary = "Отменить рейс", description = "Кассир отменяет рейс по ID")
    @ApiResponse(responseCode = "200", description = "Рейс успешно отменен")
    @ApiResponse(responseCode = "404", description = "Рейс не найден")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    public ResponseEntity<Void> cancelFlight(@PathVariable Long flightId) {
        try {
            cashierService.cancelFlight(flightId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
