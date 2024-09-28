package com.tm.dto;

import lombok.Data;

import java.util.List;

public class TouristCompanyDto {
    private Long branchId;
    private String branchName;
    private String place;
    private String website;
    private String contact;
    private String email;
    private List<TariffDto> tariffs;

    // Private constructor to enforce usage of the Builder
    private TouristCompanyDto(Builder builder) {
        this.branchId = builder.branchId;
        this.branchName = builder.branchName;
        this.place = builder.place;
        this.website = builder.website;
        this.contact = builder.contact;
        this.email = builder.email;
        this.tariffs = builder.tariffs;
    }

    // Getters
    public Long getBranchId() { return branchId; }
    public String getBranchName() { return branchName; }
    public String getPlace() { return place; }
    public String getWebsite() { return website; }
    public String getContact() { return contact; }
    public String getEmail() { return email; }
    public List<TariffDto> getTariffs() { return tariffs; }

    // Builder class
    public static class Builder {
        private Long branchId;
        private String branchName;
        private String place;
        private String website;
        private String contact;
        private String email;
        private List<TariffDto> tariffs;

        public Builder withBranchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        public Builder withBranchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        public Builder withPlace(String place) {
            this.place = place;
            return this;
        }

        public Builder withWebsite(String website) {
            this.website = website;
            return this;
        }

        public Builder withContact(String contact) {
            this.contact = contact;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withTariffs(List<TariffDto> tariffs) {
            this.tariffs = tariffs;
            return this;
        }

        public TouristCompanyDto build() {
            return new TouristCompanyDto(this);
        }
    }
}
