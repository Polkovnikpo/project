package com.example.simbirsoft.controller;

import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/manager")
@Tag(name = "Менеджер", description = "Методы для получения статистики билетов")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService statistics) {
        this.managerService = statistics;
    }

    @GetMapping("/stats")
    @Operation(summary = "Получение статистики билетов",
            description = "Возвращает количество проданных, забронированных билетов и общую выручку")
    @ApiResponse(responseCode = "200", description = "Успешное получение статистики")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("soldTickets", managerService.countTickets(TicketStatus.SOLD));
        stats.put("bookedTickets", managerService.countTickets(TicketStatus.BOOKED));
        stats.put("totalRevenue", managerService.getTotalRevenue());

        return ResponseEntity.ok(stats);
    }
}
