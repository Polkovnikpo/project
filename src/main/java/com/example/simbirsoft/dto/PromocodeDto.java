package com.example.simbirsoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Информация о промокодах")
public class PromocodeDto {
    @Schema(description = "Название промокода", example = "POPO")
    private String promoCodeName;
    @Schema(description = "Количесвто использований промокода", example = "10")
    private int usageCount;
    @Schema(description = "Время истечения срока действия промокода", example = "2025-12-31T23:59:59")
    private LocalDateTime expirationTime;

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
