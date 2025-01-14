package com.example.simbirsoft.dto;

import com.example.simbirsoft.entity.TicketStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {
    private BigDecimal price;
    private TicketStatus status;


    public BigDecimal getPrice() {
        return price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public boolean soldWithCommission;

    public boolean isSoldWithCommission() {
        return soldWithCommission;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setSoldWithCommission(boolean soldWithCommission) {
        this.soldWithCommission = soldWithCommission;
    }
}
