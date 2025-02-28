package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.AirlineDto;
import com.example.simbirsoft.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/airline")
@Tag(name = "Авиакомпании", description = "Методы управления авиакомпаниями")
public class AirlineController {
    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Создать авиакомпанию", description = "Создаёт новую авиакомпанию и возвращает её информацию")
    @ApiResponse(responseCode = "200", description = "Авиакомпания успешно создана")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    public ResponseEntity<AirlineDto> createAirline(@RequestBody AirlineDto dto) {
        AirlineDto createdAirline = airlineService.createAirline(dto);
        return ResponseEntity.ok(createdAirline);
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Обновить информацию об авиакомпании", description = "Обновляет информацию об авиакомпании по её ID")
    @ApiResponse(responseCode = "200", description = "Информация успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Авиакомпания не найдена")
    public ResponseEntity<AirlineDto> updateAirline(@PathVariable("id") Long id, @RequestBody AirlineDto dto) {
        AirlineDto updatedAirline = airlineService.updateAirline(id, dto);
        return ResponseEntity.ok(updatedAirline);
    }

    @GetMapping(value = "/get/{id}")
    @Operation(summary = "Получить информацию об авиакомпании", description = "Возвращает информацию об авиакомпании по ID")
    @ApiResponse(responseCode = "200", description = "Авиакомпания найдена")
    @ApiResponse(responseCode = "404", description = "Авиакомпания не найдена")
    public ResponseEntity<AirlineDto> getAirlineById(@PathVariable("id") Long id) {
        AirlineDto airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Удалить авиакомпанию", description = "Удаляет авиакомпанию по ID")
    @ApiResponse(responseCode = "200", description = "Авиакомпания успешно удалена")
    @ApiResponse(responseCode = "404", description = "Авиакомпания не найдена")
    public ResponseEntity<Void> deleteAirline(@PathVariable("id") Long id) {
        airlineService.deleteAirlineById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tickets/count/{airlineId}")
    @Operation(summary = "Получить количество билетов для авиакомпании", description = "Возвращает количество билетов для указанной авиакомпании по ID")
    @ApiResponse(responseCode = "200", description = "Количество билетов получено")
    @ApiResponse(responseCode = "404", description = "Авиакомпания не найдена")
    public ResponseEntity<Integer> getTicketCountByAirlineId(@PathVariable Long airlineId) {
        Integer ticketCount = airlineService.getTicketsCountByAirlineId(airlineId);
        return ResponseEntity.ok(ticketCount);
    }
}
