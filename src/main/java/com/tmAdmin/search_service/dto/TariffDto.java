package com.tmAdmin.search_service.dto;

import lombok.Data;

@Data
public class TariffDto {
    private Long tariffId;
    private String place;
    private int tariffAmount;
}