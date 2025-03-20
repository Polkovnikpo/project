package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.repository.FlightRepository;
import com.example.simbirsoft.repository.TicketRepository;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashierService {

    private final static Logger log = LoggerFactory.getLogger(CashierService.class);
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public CashierService(TicketRepository ticketRepository, UserRepository userRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
    }

    public TicketDto confirmPurchase(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Билет не найден"));

        if (ticket.getFlight().getStatus() == FlightStatus.COMPLETED) {
            throw new ServiceException(ErrorCode.UNAVAILABLE, "Рейс был завершен, нельзя забронировать билет");
        }

        if (ticket.getStatus() == TicketStatus.BOOKED) {
            throw new ServiceException(ErrorCode.VALIDATION_ERROR, "Билет не забронирован");
        }

        ticket.setStatus(TicketStatus.SOLD);
        ticketRepository.save(ticket);
        log.info("Билет с ID {} успешно куплен", ticketId);
        return mapTicketToDto(ticket);
    }

    public TicketDto cancelBooking(Long ticketId) {
        log.info("Отмена бронирования билета с ID {}", ticketId);

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Билет не найден"));

        if (ticket.getStatus() != TicketStatus.BOOKED) {
            throw new ServiceException(ErrorCode.VALIDATION_ERROR, "Билет не забронирован");
        }

        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setBookingExpirationTime(null);
        ticket.setDiscountPrice(null);
        ticketRepository.save(ticket);
        log.info("Билет с ID {} снова доступен к покупке", ticketId);
        return mapTicketToDto(ticket);
    }

    //отмена рейса по особым обстоятельствам
    @Transactional
    public void cancelFlight(Long ticketId) {
        log.info("Отмена рейса с ID {}", ticketId);
        Flight flight = flightRepository.findById(ticketId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Рейс не найден"));

        if (flight.getStatus() != FlightStatus.SCHEDULED) {
            throw new ServiceException(ErrorCode.VALIDATION_ERROR, "Невозможно отменитьь рейс(статус рейса не подходит)");
        }

        flight.setStatus(FlightStatus.CANCELED);
        flightRepository.save(flight);
        log.info("Рейс с ID {} успешно отменен", ticketId);
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isIsCommission());
        return ticketDto;
    }
}
