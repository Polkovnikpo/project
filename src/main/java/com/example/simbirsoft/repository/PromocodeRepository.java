package com.example.simbirsoft.repository;

import com.example.simbirsoft.entity.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
    Optional<Promocode> findByPromoCodeName(String promoCodeName);
}
