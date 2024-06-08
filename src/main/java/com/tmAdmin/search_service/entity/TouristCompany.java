package com.tmAdmin.search_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name = "touristcompany")
public class TouristCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    private String branchName;
    private String place;
    private String website;
    private String contact;
    private String email;

    @OneToMany(mappedBy = "touristCompany", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tariff> tariffs;
}
