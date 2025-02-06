package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class Statistics {
    private final TicketRepository ticketRepository;

    @Autowired
    public Statistics(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public long countSoldTickets(){
        return ticketRepository.countByStatus(TicketStatus.SOLD);
    }

    public long countBookedTickets(){
        return ticketRepository.countByStatus(TicketStatus.BOOKED);
    }

    public BigDecimal getTotalRevenue(){
        return ticketRepository.sumPriceByStatus(TicketStatus.SOLD);
    }
}
