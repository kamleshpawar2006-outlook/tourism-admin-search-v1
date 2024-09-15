package com.tm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name = "read-touristcompany")
public class TouristCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    private String branchName;
    private String place;
    private String website;
    private String contact;
    private String email;

    @OneToMany(mappedBy = "touristCompany", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Tariff> tariffs;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public List<Tariff> getTariffs() {
        return tariffs;
    }
}
