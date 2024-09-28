package com.tm.dto;

import lombok.Data;

public class TariffDto {
    private Long tariffId;
    private String place;
    private int tariffAmount;

    // Private constructor to enforce usage of builder
    private TariffDto(Builder builder) {
        this.tariffId = builder.tariffId;
        this.place = builder.place;
        this.tariffAmount = builder.tariffAmount;
    }

    // Getters
    public Long getTariffId() { return tariffId; }
    public String getPlace() { return place; }
    public int getTariffAmount() { return tariffAmount; }

    // Builder class
    public static class Builder {
        private Long tariffId;
        private String place;
        private int tariffAmount;

        public Builder withTariffId(Long tariffId) {
            this.tariffId = tariffId;
            return this;
        }

        public Builder withPlace(String place) {
            this.place = place;
            return this;
        }

        public Builder withTariffAmount(int tariffAmount) {
            this.tariffAmount = tariffAmount;
            return this;
        }

        public TariffDto build() {
            return new TariffDto(this);
        }
    }
}