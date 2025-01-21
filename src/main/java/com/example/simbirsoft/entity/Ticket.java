package com.example.simbirsoft.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Data
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;

    @Column(name = "is_commission", nullable = false)
    private boolean isCommission;

    public Long getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public boolean isCommission() {
        return isCommission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlight(Flight flight) {
        flight = flight;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setIsCommission(boolean commission) {
        isCommission = commission;
    }
}
