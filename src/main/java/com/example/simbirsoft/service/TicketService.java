package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.CreateTicketDto;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final FlightRepository flightRepository;

    private final AirplaneRepository airplaneRepository;

    public TicketService(TicketRepository ticketRepository,
                         FlightRepository flightRepository,
                         AirplaneRepository airplaneRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
    }


    public CreateTicketDto createTicket(CreateTicketDto createTicketDto) {
        Ticket ticket = mapCreateTicketDtoToTicket(createTicketDto);

        Flight flight = flightRepository.findById(createTicketDto.getFlightId())
                .orElseThrow(() -> new NoSuchElementException("Рейс не найден с ID = " + createTicketDto.getFlightId()));
        ticket.setFlight(flight);

        if(createTicketDto.isCommission()){
            BigDecimal commissionPrice = calculateCommission(ticket.getPrice(), createTicketDto.getCommission());
            ticket.setPrice(ticket.getPrice().add(commissionPrice));
            ticket.setIsCommission(true);
        } else {
            ticket.setIsCommission(false);
        }

        ticketRepository.save(ticket);

        CreateTicketDto result = mapTicketToCreateTicketDto(ticket);
        log.info("Билет успешно создан");
        return result;
    }

    private BigDecimal calculateCommission(BigDecimal basePrice, BigDecimal commissionRate) {
        BigDecimal commission = basePrice.multiply(commissionRate.divide(BigDecimal.valueOf(100)));
        return basePrice.add(commission).setScale(2, RoundingMode.HALF_UP);
    }

    public TicketDto updateTicketById(Long id, TicketDto dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Билет с ID {} не найден", id);
                    return new IllegalArgumentException("Билет не найден");
                });

        ticket.setPrice(dto.getPrice());
        ticket.setStatus(dto.getStatus());
        ticketRepository.save(ticket);

        log.info("Билет с ID {} успешно обновлен", id);

        TicketDto ticketDto = mapTicketToDto(ticket);
        return ticketDto;
    }

    public TicketDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Билет с ID {} не найден", id);
                    return new IllegalArgumentException("Билет не найден");
                });

        TicketDto ticketDto = mapTicketToDto(ticket);
        log.info("Билет с ID {} успешно получен: {}", id, ticketDto);
        return ticketDto;
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
        Ticket ticket = new Ticket();
        ticket.setPrice(ticketDto.getPrice());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setIsCommission(ticketDto.isCommission());
        return ticket;
    }

   public CreateTicketDto mapTicketToCreateTicketDto(Ticket ticket) {
        CreateTicketDto createTicketDto = new CreateTicketDto();
        createTicketDto.setPrice(ticket.getPrice());
        createTicketDto.setStatus(ticket.getStatus());
        createTicketDto.setCommission(ticket.isCommission());
        return  createTicketDto;
    }

    public Ticket mapCreateTicketDtoToTicket(CreateTicketDto createTicketDto) {
        Ticket ticket = new Ticket();
        ticket.setPrice(createTicketDto.getPrice());
        ticket.setStatus(createTicketDto.getStatus());
        ticket.setIsCommission(createTicketDto.isCommission());
        return ticket;
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isCommission());
        return ticketDto;
    }
}
