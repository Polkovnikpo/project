package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Flight;
import lombok.Locked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> getFlightsByAirplaneId(Long airplaneId);
    List<Flight> findByStartingPoint(String startingPoint);
}
