package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.repository.AirplaneRepository;
import com.example.simbirsoft.repository.FlightRepository;
import com.example.simbirsoft.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final FlightRepository flightRepository;

    private final AirplaneRepository airplaneRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         FlightRepository flightRepository,
                         AirplaneRepository airplaneRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
    }

    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = mapDtoToTicket(ticketDto);
        Flight flight = flightRepository.findById(ticketDto.getFlightId()).orElseThrow();
        ticket.setFlight(flight);
        ticketRepository.save(ticket);
        TicketDto dto = mapTicketToDto(ticket);
        log.info("Билет успешно создан");
        return dto;
    }

    public TicketDto createTicketWithCommission(TicketDto dto, BigDecimal commissionRate) {
        log.info("Создание билета с комиссией: {}, ставка комиссии: {}", dto, commissionRate);
        Ticket ticket = mapDtoToTicket(dto);
        int commissionPrice = calculateCommission(ticket.getPrice(), commissionRate);
        ticket.setPrice(BigDecimal.valueOf(commissionPrice));
        ticket.setIsCommission(true);
        ticketRepository.save(ticket);
        TicketDto result = mapTicketToDto(ticket);
        log.info("Билет с комиссией успешно создан: {}", result);
        return result;
    }

    private int calculateCommission(BigDecimal basePrice, BigDecimal commissionRate) {
        BigDecimal commission = basePrice.multiply(commissionRate.divide(BigDecimal.valueOf(100)));
        return basePrice.add(commission).setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public TicketDto updateTicketById(Long id, TicketDto dto) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = new Ticket();
            ticket.setPrice(dto.getPrice());
            ticket.setStatus(dto.getStatus());
            ticketRepository.save(ticket);
            log.info("Билет с ID {} успешно обновлен", id);
            TicketDto ticketDto = mapTicketToDto(ticket);
            return ticketDto;
        } else {
            log.warn("Билет с ID {} не найден для обновления", id);
            return null;
        }
    }

    public TicketDto getTicketById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            TicketDto ticketDto = mapTicketToDto(ticketOptional.get());
            log.info("Билет с ID {} успешно получен: {}", id, ticketDto);
            return ticketDto;
        } else {
            log.warn("Билет с ID {} не найден для получения", id);
            return null;
        }
    }

    public void deleteTicketById(Long id) {
        try {
            ticketRepository.deleteById(id);
            log.info("Билет с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении билета с ID {}: {}", id, e.getMessage(), e);
        }
    }

    public Integer getTicketCountByStartingPoint(String startingPoint) {
        List<Flight> flights = flightRepository.findByStartingPoint(startingPoint);

        int count = flights.stream()
                .flatMap(flight -> flight.getTickets().stream())
                .toList().size();
        log.info("Количество билетов для точки отправления {}: {}", startingPoint, count);
        return count;
    }

    public BigDecimal getAverageCommissionInRubles() {
        List<Ticket> ticketsCommission = ticketRepository.findAllByIsCommission(true);

        if (ticketsCommission.isEmpty()) {
            log.info("Билеты с комиссией не найдены");
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (Ticket ticket : ticketsCommission) {
            BigDecimal ticketPrice = ticket.getPrice();

            if (ticketPrice != null) {
                BigDecimal result = ticketPrice.multiply(BigDecimal.valueOf(0.025));
                sum = sum.add(result);
            }
        }

        BigDecimal average = sum.divide(BigDecimal.valueOf(ticketsCommission.size()), 2, RoundingMode.HALF_UP);
        log.info("Средняя комиссия: {}", average);
        return average;
    }

    public Ticket mapDtoToTicket(TicketDto ticketDto) {
        log.debug("Маппинг объекта TicketDto в Ticket: {}", ticketDto);
        Ticket ticket = new Ticket();
        ticket.setPrice(ticketDto.getPrice());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setIsCommission(ticketDto.isCommission());
        return ticket;
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        log.debug("Маппинг объекта Ticket в TicketDto: {}", ticket);
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isIsCommission());
        return ticketDto;
    }
}
