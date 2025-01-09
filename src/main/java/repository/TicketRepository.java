package repository;

import entity.Ticket;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
