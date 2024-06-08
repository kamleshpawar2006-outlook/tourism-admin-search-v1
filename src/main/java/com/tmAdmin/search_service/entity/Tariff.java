package com.tmAdmin.search_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tariff", uniqueConstraints = @UniqueConstraint(columnNames = {"branchId", "place"}))
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariffId;
    private String place;
    private int tariffAmount;

    @ManyToOne
    @JoinColumn(name = "branchId", nullable = false)
    private TouristCompany touristCompany;

    @Override
    public String toString() {
        return "Tariff{" +
                "tariffId=" + tariffId +
                ", place='" + place + '\'' +
                ", tariff=" + tariffAmount +
                '}';
    }

}
