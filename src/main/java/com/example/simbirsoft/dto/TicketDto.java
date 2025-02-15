package com.example.simbirsoft.dto;

import com.example.simbirsoft.entity.TicketStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketDto {
    private BigDecimal price;
    private TicketStatus status;
    private boolean isCommission;
    private long flightId;

    public BigDecimal getPrice() {
        return price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public boolean isCommission() {
        return isCommission;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setCommission(boolean commission) {
        isCommission = commission;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }
}
