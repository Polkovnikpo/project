package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> getTicketsByFlightId(Long flightId);
}
