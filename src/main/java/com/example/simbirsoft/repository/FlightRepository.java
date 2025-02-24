package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> getFlightsByAirplaneId(Long airplaneId);

    List<Flight> findByStartingPoint(String startingPoint);

    @Modifying
    @Query("UPDATE Flight f SET f.status = 'IN_PROCESS' " +
            "WHERE f.status = 'SCHEDULED' AND f.departureTime <= :now")
    void updateStatusInProcess(@Param("now")LocalDateTime now);

    @Modifying
    @Query("UPDATE Flight f SET f.status = 'COMPLETED' " +
            "WHERE f.status = 'IN_PROCESS' AND f.departureTime <= :now")
    void updateStatusInCompleted(@Param("now")LocalDateTime now);
}
