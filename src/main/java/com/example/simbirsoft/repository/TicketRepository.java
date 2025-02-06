package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Flight;
import com.example.simbirsoft.entity.FlightStatus;
import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> getTicketsByFlightId(Long flightId);

    List<Ticket> findAllByIsCommission(boolean isCommission);

    long countByStatus(TicketStatus status);

    @Query("SELECT SUM(t.price) FROM Ticket t WHERE t.status = :status")
    BigDecimal sumPriceByStatus(@Param("status") TicketStatus status);

    Optional<Ticket> findFirstByFlightAndStatus(Flight flight, TicketStatus status);

    List<Ticket> findAllByStatusAndBookingExpirationTimeBefore(TicketStatus status, LocalDateTime time);
}
