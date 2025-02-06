package com.example.simbirsoft.controller;

import com.example.simbirsoft.service.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
    private final Statistics managerService;

    @Autowired
    public ManagerController(Statistics statistics) {
        this.managerService = statistics;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("soldTickets", managerService.countSoldTickets());
        stats.put("bookedTickets", managerService.countBookedTickets());
        stats.put("totalTickets", managerService.getTotalRevenue());

        return ResponseEntity.ok(stats);
    }
}
