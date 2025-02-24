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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashierService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public CashierService(TicketRepository ticketRepository, UserRepository userRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
    }

    public TicketDto confirmPurchase(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Билет не найден"));

        if (ticket.getFlight().getStatus() == FlightStatus.COMPLETED) {
            throw new IllegalArgumentException("Рейс был завершен, нельзя забронировать билет");
        }

        if (ticket.getStatus() == TicketStatus.BOOKED) {
            ticket.setStatus(TicketStatus.SOLD);
            ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException("Билет не забронирован");
        }
        return mapTicketToDto(ticket);
    }

    public TicketDto cancelBooking(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Билет не найден"));

        if (ticket.getStatus() == TicketStatus.BOOKED) {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticket.setBookingExpirationTime(null);
            ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException("Билет не забронирован");
        }
        return mapTicketToDto(ticket);
    }

    //отмена рейса по особым обстоятельствам
    @Transactional
    public String cancelFlight(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();

            if (flight.getStatus() == FlightStatus.SCHEDULED) {
                flight.setStatus(FlightStatus.CANCELED);
                flightRepository.save(flight);
                return "Рейс успешно отменен";
            } else {
                return "Невозможно отменить рейс(стфтус не подходит";
            }
        }
        return "Рейс не найден";
    }

    public TicketDto mapTicketToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setStatus(ticket.getStatus());
        ticketDto.setCommission(ticket.isIsCommission());
        return ticketDto;
    }
}
