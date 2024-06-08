package com.tmAdmin.search_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class TouristCompanyDto {
    private Long branchId;
    private String branchName;
    private String place;
    private String website;
    private String contact;
    private String email;
    private List<TariffDto> tariffs;
}
