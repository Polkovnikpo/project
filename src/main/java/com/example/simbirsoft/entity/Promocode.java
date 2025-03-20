package com.example.simbirsoft.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "promo_codes")
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String promoCodeName;
    @Column(name = "usage_count", nullable = false)
    private int usageCount;
    @Column(name = "expiration_date")
    private LocalDateTime expirationTime;

    public Promocode(Long id, String promoCodeName, int usageCount, LocalDateTime expirationTime) {
        this.id = id;
        this.promoCodeName = promoCodeName;
        this.usageCount = usageCount;
        this.expirationTime = expirationTime;
    }

    public Promocode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromoCodeName() {
        return promoCodeName;
    }

    public void setPromoCodeName(String promoCodeName) {
        this.promoCodeName = promoCodeName;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
