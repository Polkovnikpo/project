package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ManagerService {

    private final TicketRepository ticketRepository;

    public ManagerService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public long countTickets(TicketStatus status){
        return ticketRepository.countByStatus(status);
    }

    public BigDecimal getTotalRevenue(){
        return ticketRepository.sumPriceByStatus(TicketStatus.SOLD);
    }
}
