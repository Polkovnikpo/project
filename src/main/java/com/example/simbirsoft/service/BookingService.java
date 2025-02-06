package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.repository.FlightRepository;
import com.example.simbirsoft.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public BookingService(TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    @Value("${ticket.booking.expiration.minutes}")
    private int bookingExpirationMinutes;

    public Ticket bookTicket(String startingPoint, Long userId) {
        Flight flight = flightRepository.findByStartingPoint(startingPoint)
                .orElseThrow(() -> new IllegalArgumentException("Рейс с данной отправной точкой не найден"));

        if (flight.getStatus() == FlightStatus.COMPLETED) {
            throw new IllegalArgumentException("Нельзя купить билет на выполненый рейс");
        }

        Ticket ticket = ticketRepository.findFirstByFlightAndStatus(flight, TicketStatus.AVAILABLE)
                .orElseThrow(() -> new IllegalArgumentException("Нет доступных билетов на этот рейс"));

        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setBookingExpirationTime(LocalDateTime.now().plusMinutes(bookingExpirationMinutes));
        ticketRepository.save(ticket);

        return ticket;
    }

    @Scheduled(fixedRate = 60000)
    public void releaseExpiredBookings() {
        List<Ticket> expiredTickets = ticketRepository.findAllByStatusAndBookingExpirationTimeBefore(
                TicketStatus.BOOKED, LocalDateTime.now());

        for (Ticket ticket : expiredTickets) {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setBookingExpirationTime(null);
            ticketRepository.save(ticket);
        }
    }
}
