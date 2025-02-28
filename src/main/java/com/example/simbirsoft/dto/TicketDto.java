package com.example.simbirsoft.dto;

import com.example.simbirsoft.entity.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Информация о билете")
public class TicketDto {
    @Schema(description = "Цена билета", example = "1200")
    private BigDecimal price;

    @Schema(description = "Статус билета", example = "BOOKED")
    private TicketStatus status;

    @Schema(description = "Комиссия за билет(true - есть комиссия, false - нет комиссии)", example = "true")
    private boolean isCommission;

    @Schema(description = "Идентификатор рейса, на который был куплен билет", example = "123")
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
