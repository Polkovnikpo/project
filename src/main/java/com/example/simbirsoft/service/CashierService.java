package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.repository.TicketRepository;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashierService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public CashierService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket confirmPurchase(Long ticketId, Long cashierId) {
        User cashier = userRepository.findById(cashierId).orElseThrow(() -> new IllegalArgumentException("Кассир не найден"));

        if (!cashier.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_CASHIER"))) {
            throw new SecurityException("Только кассир может подтверждать покупку билета");
        }

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
        return ticket;
    }

    public Ticket cancelBooking(Long ticketId, Long cashierId) {
        User cashier = userRepository.findById(cashierId).orElseThrow(() -> new IllegalArgumentException("Кассир не найден"));

        if (!cashier.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_CASHIER"))) {
            throw new SecurityException("Только кассир может отменить бронь");
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Билет не найден"));

        if (ticket.getStatus() == TicketStatus.BOOKED) {
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException("Билет не забронирован");
        }
        return ticket;
    }
}
