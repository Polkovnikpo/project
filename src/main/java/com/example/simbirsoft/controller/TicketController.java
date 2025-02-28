package com.example.simbirsoft.controller;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.repository.TicketRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.simbirsoft.service.TicketService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ticket")
@Tag(name = "Билеты", description = "Методы управления билетами")
public class TicketController {
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    public TicketController(TicketService ticketService, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Создать билет", description = "Создает билет и возвращает его данные")
    @ApiResponse(responseCode = "200", description = "Билет успешно создан")
    @ApiResponse(responseCode = "404", description = "Некорректные данные запроса")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto dto) {
        TicketDto createTicket = ticketService.createTicket(dto);
        return ResponseEntity.ok(createTicket);
    }

    @PostMapping(value = "/commission")
    @Operation(summary = "Создать билет с комиссией", description = "Создает билет с комиссией")
    @ApiResponse(responseCode = "200", description = "Билет успешно создан")
    @ApiResponse(responseCode = "404", description = "Некорректыне данные запроса")
    ResponseEntity<TicketDto> createTicketWithCommission(@RequestBody TicketDto dto) {
        TicketDto ticketWithCommission = ticketService.createTicketWithCommission(dto);
        return ResponseEntity.ok(ticketWithCommission);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Обновить билет", description = "Обновляет данные билета по ID")
    @ApiResponse(responseCode = "200", description = "Билет успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Билет не найден")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable("id") Long id, @RequestBody TicketDto dto) {
        TicketDto updateTicket = ticketService.updateTicketById(id, dto);
        return ResponseEntity.ok(updateTicket);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получить билет по ID", description = "Возвращает данные билета по его ID")
    @ApiResponse(responseCode = "200", description = "Билет найден")
    @ApiResponse(responseCode = "404", description = "Билет не найден")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("id") Long id) {
        TicketDto getTicket = ticketService.getTicketById(id);
        return ResponseEntity.ok(getTicket);
    }

    @GetMapping(value = "/ticketCountByStartingPoint")
    @Operation(summary = "Получить количество билетов по отправной точке", description = "Возвращает количесвто билет с указанной отправной точкой")
    @ApiResponse(responseCode = "200", description = "Количество билетов успешно получено")
    @ApiResponse(responseCode = "404", description = "Некорректные параметры запроса")
    public ResponseEntity<Integer> getTicketCountByStartingPoint(@RequestParam("startingPoint") String startingPoint) {
        int ticketCount = ticketService.getTicketCountByStartingPoint(startingPoint);
        return ResponseEntity.ok(ticketCount);
    }

    @GetMapping(value = "/averageCommissionInRubles")
    @Operation(summary = "Средняя комиссия в рублях", description = "Возвращает среднюю комиссию по всем билетам")
    @ApiResponse(responseCode = "200", description = "Средняя комиссия успешно получена")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    public ResponseEntity<BigDecimal> getAverageCommissionInRubles() {
        BigDecimal commission = ticketService.getAverageCommissionInRubles();
        return ResponseEntity.ok(commission);
    }

    @GetMapping("/showSold")
    @Operation(summary = "Получить список билетов", description = "Возвращает список билетов с фильтрацией по проданным билетам")
    @ApiResponse(responseCode = "200", description = "Список билетов успешно получен")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    public ResponseEntity<List<TicketDto>> getTickets(@RequestParam(value = "showSold", defaultValue = "false") boolean showSold) {
        List<TicketDto> filteredTickets = ticketService.getAllTickets(showSold);
        return ResponseEntity.ok(filteredTickets);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удалить билет", description = "Удаляет билет по ID")
    @ApiResponse(responseCode = "200", description = "Билет успешно удален")
    @ApiResponse(responseCode = "404", description = "Билет не найден")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.noContent().build();
    }
}

