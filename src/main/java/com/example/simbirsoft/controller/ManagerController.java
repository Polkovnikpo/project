package com.example.simbirsoft.controller;

import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService statistics) {
        this.managerService = statistics;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("soldTickets", managerService.countTickets(TicketStatus.SOLD));
        stats.put("bookedTickets", managerService.countTickets(TicketStatus.BOOKED));
        stats.put("totalTickets", managerService.getTotalRevenue());

        return ResponseEntity.ok(stats);
    }
}
