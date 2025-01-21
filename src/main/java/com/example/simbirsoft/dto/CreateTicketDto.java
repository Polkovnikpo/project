package com.example.simbirsoft.dto;

import com.example.simbirsoft.entity.TicketStatus;

import java.math.BigDecimal;

public class CreateTicketDto {
    private BigDecimal price;
    private TicketStatus status;
    private boolean isCommission;
    private long flightId;
    private BigDecimal commission;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public boolean isCommission() {
        return isCommission;
    }

    public void setCommission(boolean commission) {
        isCommission = commission;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
}
