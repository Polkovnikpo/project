package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.repository.FlightRepository;
import com.example.simbirsoft.repository.TicketRepository;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final static Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingService(TicketRepository ticketRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Value("${ticket.booking.expiration.minutes}")
    private int bookingExpirationMinutes;

    @Transactional
    public TicketDto bookTicket(Long ticketId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Пользователь не аутентифицирован");
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Покупатель не найден"));
        List<Ticket> t = ticketRepository.findAll();
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Билет с ID: " + ticketId + " не найден"));
        Flight flight = ticket.getFlight();
        if (flight.getStatus() == FlightStatus.COMPLETED) {
            throw new IllegalArgumentException("Нельзя купить билет на завершенный рейс");
        }

        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setBookingExpirationTime(LocalDateTime.now().plusMinutes(bookingExpirationMinutes));
        ticket.setUser(user);
        ticketRepository.save(ticket);

        TicketDto ticketDto = mapTicketToDto(ticket);
        ticketDto.setMessage("Билет успешно забронирован");

        return ticketDto;
    }


    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredBookings() {
        ticketRepository.releaseExpiredBookings(LocalDateTime.now());
        log.info("Бронь снята, так как время для подтвержения брони окончено");
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setFlightId(ticket.getFlight().getId());
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isIsCommission());
        return ticketDto;
    }
}
