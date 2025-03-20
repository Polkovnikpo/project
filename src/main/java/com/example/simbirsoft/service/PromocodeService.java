package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.entity.Promocode;
import com.example.simbirsoft.repository.PromocodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PromocodeService {

    private final static Logger log = LoggerFactory.getLogger(PromocodeService.class);
    private final PromocodeRepository promoCodesRepository;

    public PromocodeService(PromocodeRepository promoCodesRepository) {
        this.promoCodesRepository = promoCodesRepository;
    }

    public void applyPromoCodes(Ticket ticket, String promoCode) {
        Promocode promo = promoCodesRepository.findByPromoCodeName(promoCode)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND, "Промокод не найден"));

        if (promo.getExpirationTime() != null && promo.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ErrorCode.UNAVAILABLE, "Промокод истек");
        }

        if (promo.getUsageCount() <= 0) {
            throw new ServiceException(ErrorCode.UNAVAILABLE, "Промокод больше нельзя использовать");
        }

        // Скидка 10%
        BigDecimal discount = ticket.getPrice().multiply(BigDecimal.valueOf(0.1));

        ticket.setDiscountPrice(ticket.getPrice().subtract(discount));

        promo.setUsageCount(promo.getUsageCount() - 1);
        promoCodesRepository.save(promo);

        log.info("Промокод '{}' применен, скидка: {}, цена билета с промокдом: {}", promoCode, discount, ticket.getDiscountPrice());
    }
}

