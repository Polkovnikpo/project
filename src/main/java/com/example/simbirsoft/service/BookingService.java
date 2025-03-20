package com.example.simbirsoft.service;

import com.example.simbirsoft.dto.TicketDto;
import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.repository.TicketRepository;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final static Logger log = LoggerFactory.getLogger(BookingService.class);
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PromocodeService promoCodeService;

    public BookingService(TicketRepository ticketRepository,
                          UserRepository userRepository,
                          PromocodeService promoCodeService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.promoCodeService = promoCodeService;
    }

    @Value("${ticket.booking.expiration.minutes}")
    private int bookingExpirationMinutes;

    @Transactional
    public TicketDto bookTicket(Long ticketId, String promoCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Пользователь не аутентифицирован");
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Покупатель не найден"));

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Билет с ID: " + ticketId + " не найден"));

        Flight flight = ticket.getFlight();
        if (flight == null) {
            throw new ServiceException(ErrorCode.NOT_FOUND, "Рейс не найден");
        }

        if (flight.getStatus() == FlightStatus.COMPLETED) {
            throw new ServiceException(ErrorCode.UNAVAILABLE, "Нельзя купить билет на завершенный рейс");
        }

        if (ticket.getStatus() == TicketStatus.BOOKED || ticket.getStatus() == TicketStatus.SOLD) {
            throw new ServiceException(ErrorCode.UNAVAILABLE, "Нельзя забронировать билет, так как его статус: " + ticket.getStatus());
        }

        if (promoCode != null && !promoCode.isEmpty()) {
            promoCodeService.applyPromoCodes(ticket, promoCode);
        }

        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setBookingExpirationTime(LocalDateTime.now().plusMinutes(bookingExpirationMinutes));
        ticket.setUser(user);
        ticketRepository.save(ticket);

        TicketDto ticketDto = mapTicketToDto(ticket);
        log.info("Билет с ID {} успешно забронирован для пользователя: {}", ticket.getId(), username);
        return ticketDto;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredBookings() {
        ticketRepository.releaseExpiredBookings(LocalDateTime.now());
        log.info("Бронь снята, так как время для подтверждения брони окончено");
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setFlightId(ticket.getFlight().getId());
        ticketDto.setPrice(ticket.getDiscountPrice() != null ? ticket.getDiscountPrice() : ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isIsCommission());
        return ticketDto;
    }
}
