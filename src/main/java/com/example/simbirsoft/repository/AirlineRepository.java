package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline,Long> {
}
