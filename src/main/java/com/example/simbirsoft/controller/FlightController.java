package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.FlightDto;
import com.example.simbirsoft.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
@Tag(name = "Рейсы", description = "Методы управления рейсами")
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    @Operation(summary = "Создать новый рейс", description = "Создает рейс и возвращает информацию о рейсе")
    @ApiResponse(responseCode = "200", description = "Рейс успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto dto) {
        FlightDto createFlight = flightService.createFlight(dto);
        return ResponseEntity.ok(createFlight);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Обновить рейс", description = "Обновляет данные рейса по ID")
    @ApiResponse(responseCode = "200", description = "Рейс успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Рейс не найден")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable("id") Long id, @RequestBody FlightDto dto) {
        FlightDto updateFlight = flightService.updateFlight(id, dto);
        return ResponseEntity.ok(updateFlight);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получить рейс по ID", description = "Возвращает информацию о рейсе по ID")
    @ApiResponse(responseCode = "200", description = "Рейс найден")
    @ApiResponse(responseCode = "404", description = "Рейс не найден")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable("id") Long id) {
        FlightDto getFlight = flightService.getFlightById(id);
        return ResponseEntity.ok(getFlight);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаляет рейс", description = "Удаление рейса по ID")
    @ApiResponse(responseCode = "200", description = "Рейс успешно удален")
    @ApiResponse(responseCode = "404", description = "Рейс не найден")
    public ResponseEntity<Void> deleteFlightById(@PathVariable("id") Long id) {
        flightService.deleteFlightDto(id);
        return ResponseEntity.noContent().build();
    }
}
