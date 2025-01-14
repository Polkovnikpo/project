package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> getFlightsByAirplaneId(Long airplaneId);

    List<Flight> findByStartingPoint(String startingPoint);
}
