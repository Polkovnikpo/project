package com.example.simbirsoft.service;

import com.example.simbirsoft.entity.Ticket;
import com.example.simbirsoft.entity.TicketStatus;
import com.example.simbirsoft.exception.ErrorCode;
import com.example.simbirsoft.exception.ServiceException;
import com.example.simbirsoft.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
public class ManagerService {
    private final static Logger log = LoggerFactory.getLogger(ManagerService.class);

    private final TicketRepository ticketRepository;

    public ManagerService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public long countTickets(TicketStatus status) {
        try {
            long count = ticketRepository.countByStatus(status);
            log.info("Количество билетов со статусом {}: {}", status, count);
            return count;
        } catch (Exception e) {
            log.error("Ошибка при подсчете билетов со статусом {}: {}", status, e.getMessage());
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при получении билетов");
        }
    }

    public BigDecimal getTotalRevenue() {
        try {
            BigDecimal totalRevenue = ticketRepository.sumPriceByStatus(TicketStatus.SOLD);
            log.info("Общая выручка от проданных билетов: {}", totalRevenue);
            return totalRevenue;
        } catch (Exception e) {
            log.error("Ошибка при рассчете выручки : {}", e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при рассчете выручки");
        }
    }

    public BigDecimal getAverageCommissionInRubles() {
        try {
            List<Ticket> ticketsCommission = ticketRepository.findAllByIsCommission(true);

            if (ticketsCommission.isEmpty()) {
                log.info("Билеты с комиссией не найдены");
                throw new ServiceException(ErrorCode.NOT_FOUND, "Билеты с комиссией не найден");
            }

            BigDecimal sum = ticketsCommission.stream()
                    .map(Ticket::getPrice)
                    .filter(Objects::nonNull)
                    .map(price -> price.multiply(BigDecimal.valueOf(0.025)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal average = sum.divide(BigDecimal.valueOf(ticketsCommission.size()), 2, RoundingMode.HALF_UP);
            log.info("Средняя комиссия: {}", average);
            return average;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ошибка при рассчете средней комиссии: {}", e.getMessage(), e);
            throw new ServiceException(ErrorCode.DATABASE_ERROR, "Ошибка при рассчете средней комиссии");
        }
    }
}
