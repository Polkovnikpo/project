package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.repository.FlightRepository;
import com.example.simbirsoft.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final static Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    @Value("${ticket.commission}")
    private BigDecimal commission;

    public TicketService(TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    public TicketDto createTicket(TicketDto ticketDto) {
        try {
            Ticket ticket = mapDtoToTicket(ticketDto);
            Flight flight = flightRepository.findById(ticketDto.getFlightId())
                    .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Рейс не найден"));
            ticket.setFlight(flight);
            ticketRepository.save(ticket);
            TicketDto dto = mapTicketToDto(ticket);
            log.info("Билет успешно создан: {}", dto);
            return dto;
        } catch (Exception e) {
            log.error("Ошибка при создании билета: {}", e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при создании билета");
        }
    }

    public TicketDto createTicketWithCommission(TicketDto dto) {
        try {
            log.info("Создание билета с комиссией: {}, ставка комиссии: {}", dto, commission);
            Ticket ticket = mapDtoToTicket(dto);
            int commissionPrice = calculateCommission(ticket.getPrice(), commission);
            ticket.setPrice(BigDecimal.valueOf(commissionPrice));
            ticket.setIsCommission(true);
            ticketRepository.save(ticket);
            TicketDto result = mapTicketToDto(ticket);
            log.info("Билет с комиссией успешно создан: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Ошибка при создании билета с комиссией: {}", e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при создании билета с комиссией");
        }
    }

    private int calculateCommission(BigDecimal basePrice, BigDecimal commissionRate) {
        BigDecimal commission = basePrice.multiply(commissionRate.divide(BigDecimal.valueOf(100)));
        return basePrice.add(commission).setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public TicketDto updateTicketById(Long id, TicketDto dto) {
        try {
            Ticket ticket = ticketRepository.findById(id)
                    .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Билет не найден"));

            ticket.setPrice(dto.getPrice());
            ticket.setStatus(dto.getStatus());
            ticketRepository.save(ticket);
            log.info("Билет с ID {} успешно обновлен", id);
            return mapTicketToDto(ticket);
        } catch (Exception e) {
            log.error("Ошибка при обновлении билета с ID {}: {}", id, e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при обновлении билета");
        }
    }

    public TicketDto getTicketById(Long id) {
        try {
            Ticket ticket = ticketRepository.findById(id)
                    .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Билет не найден"));
            TicketDto ticketDto = mapTicketToDto(ticket);
            log.info("Билет с ID {} успешно получен: {}", id, ticketDto);
            return ticketDto;
        } catch (Exception e) {
            log.error("Ошибка при получении билета с ID {}: {}", id, e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при получении билета");
        }
    }

    public void deleteTicketById(Long id) {
        try {
            if (!ticketRepository.existsById(id)) {
                throw new ServiceException(ErrorCode.NOT_FOUND, "Билет не найден");
            }
            ticketRepository.deleteById(id);
            log.info("Билет с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении билета с ID {}: {}", id, e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при удалении билета");
        }
    }

    public Integer getTicketCountByStartingPoint(String startingPoint) {
        try {
            List<Flight> flights = flightRepository.findByStartingPoint(startingPoint);

            if (flights.isEmpty()) {
                throw new ServiceException(ErrorCode.NOT_FOUND, "Рейсы с данной отправной точкой не найдены");
            }

            int count = flights.stream()
                    .flatMap(flight -> flight.getTickets().stream())
                    .toList()
                    .size();

            log.info("Количество билетов для точки отправления {}: {}", startingPoint, count);
            return count;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ошибка при получении количества билетов по отправной точке {}: {}", startingPoint, e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при получении количества билетов по отправной точке");
        }
    }

    public List<TicketDto> getAllTickets(boolean showSold) {
        try {
            List<Ticket> allTickets = ticketRepository.findAll();
            List<TicketDto> result = allTickets.stream()
                    .filter(ticket -> showSold || ticket.getStatus() != TicketStatus.SOLD)
                    .map(this::mapTicketToDto)
                    .collect(Collectors.toList());
            log.info("Получено {} билетов (showSold={})", result.size(), showSold);
            return result;
        } catch (Exception e) {
            log.error("Ошибка при получении билетов: {}", e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при получении билетов");
        }
    }

    public List<TicketDto> getTicketsByPrice(BigDecimal price) {
        try {
            List<TicketDto> tickets = ticketRepository.findAll().stream()
                    .filter(ticket -> ticket.getPrice().compareTo(price) > 0)
                    .map(this::mapTicketToDto)
                    .collect(Collectors.toList());
            log.info("Найдено {} билетов с ценой выше: {}", tickets.size(), price);
            return tickets;
        } catch (Exception e) {
            log.error("Ошибка при получении билетов по цене: {}", e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при получении по цене");
        }
    }

    public Ticket mapDtoToTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setPrice(ticketDto.getPrice());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setIsCommission(ticketDto.isCommission());
        return ticket;
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isIsCommission());
        return ticketDto;
    }
}
