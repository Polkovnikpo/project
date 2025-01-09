package repository;

import entity.Flight;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
