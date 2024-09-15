package com.tm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "read-tariff", uniqueConstraints = @UniqueConstraint(columnNames = {"branchId", "place"}))
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariffId;
    private String place;
    private int tariffAmount;

    @ManyToOne
    @JoinColumn(name = "branchId", nullable = false)
    @JsonBackReference
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
