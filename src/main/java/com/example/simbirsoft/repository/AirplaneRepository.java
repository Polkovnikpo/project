package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> getAirplanesByAirlineId(Long airlineId);
}
