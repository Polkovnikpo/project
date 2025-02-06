package com.example.simbirsoft.entity;

import com.example.simbirsoft.security.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime bookingExpirationTime;

    @Column(nullable = false)
    private boolean isCommission;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;


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

    public boolean isIsCommission() {
        return isCommission;
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

    public void setIsCommission(boolean commission) {
        isCommission = commission;
    }

    public boolean isCommission() {
        return isCommission;
    }

    public void setCommission(boolean commission) {
        isCommission = commission;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCashier() {
        return cashier;
    }

    public void setCashier(User cashier) {
        this.cashier = cashier;
    }

    public LocalDateTime getBookingExpirationTime() {
        return bookingExpirationTime;
    }

    public void setBookingExpirationTime(LocalDateTime bookingExpirationTime) {
        this.bookingExpirationTime = bookingExpirationTime;
    }
}
