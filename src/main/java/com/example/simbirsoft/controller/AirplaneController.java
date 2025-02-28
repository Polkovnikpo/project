package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.AirplaneDto;
import com.example.simbirsoft.service.AirplaneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airplane")
@Tag(name = "Самолеты", description = "Методы управления самолетами")
public class AirplaneController {

    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping
    @Operation(summary = "Создать самолет", description = "Создает новый самолет и возвращает его информацию")
    @ApiResponse(responseCode = "200", description = "Самолет успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    public ResponseEntity<AirplaneDto> createAirplane(@RequestBody AirplaneDto dto) {
        AirplaneDto createdAirplane = airplaneService.createAirplane(dto);
        return ResponseEntity.ok(createdAirplane);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о самолете", description = "Обновляет информацию о самолете по ID")
    @ApiResponse(responseCode = "200", description = "Информация успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Самолет не найден")
    public ResponseEntity<AirplaneDto> updateAirplane(@PathVariable("id") Long id, @RequestBody AirplaneDto dto) {
        AirplaneDto updatedAirplane = airplaneService.updateAirplane(id, dto);
        return ResponseEntity.ok(updatedAirplane);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о самолете", description = "Возвращает информацию о самолете по ID")
    @ApiResponse(responseCode = "200", description = "Самолет найден")
    @ApiResponse(responseCode = "404", description = "Самолет не найден")
    public ResponseEntity<AirplaneDto> getAirplaneById(@PathVariable("id") Long id) {
        AirplaneDto airplaneById = airplaneService.getAirplaneById(id);
        return ResponseEntity.ok(airplaneById);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить самолет", description = "Удаляет самолет по ID")
    @ApiResponse(responseCode = "200", description = "Самолет успешно удален")
    @ApiResponse(responseCode = "404", description = "Самолет не найден")
    public ResponseEntity<Void> deleteAirplaneById(@PathVariable("id") Long id) {
        airplaneService.deleteAirplaneById(id);
        return ResponseEntity.ok().build();
    }
}
