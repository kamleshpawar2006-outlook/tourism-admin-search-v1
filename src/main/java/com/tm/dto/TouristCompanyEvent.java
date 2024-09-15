package com.tm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tm.entity.TouristCompany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TouristCompanyEvent {
    private String eventType;
    private TouristCompany touristCompany;
}
