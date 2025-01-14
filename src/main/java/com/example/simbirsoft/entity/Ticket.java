package com.example.simbirsoft.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

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

    @Column
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column
    private TicketStatus status;

    @Column(name = "sold_with_commission", nullable = false)
    private boolean isSoldWithCommission;

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

    public boolean isSoldWithCommission() {
        return isSoldWithCommission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setSoldWithCommission(boolean isSoldWithCommission) {
        this.isSoldWithCommission = isSoldWithCommission;
    }
}
