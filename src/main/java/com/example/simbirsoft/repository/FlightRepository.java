package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> getFlightsByAirplaneId(Long airplaneId);

    Optional<Flight> findByStartingPoint(String startingPoint);
}
