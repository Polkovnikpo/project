package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    List<Airplane> getAirplanesByAirlineId(Long airlineId);
}
