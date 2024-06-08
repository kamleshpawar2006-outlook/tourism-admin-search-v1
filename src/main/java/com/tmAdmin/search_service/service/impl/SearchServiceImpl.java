package com.tmAdmin.search_service.service.impl;

import com.tmAdmin.search_service.dto.TariffDto;
import com.tmAdmin.search_service.dto.TouristCompanyDto;
import com.tmAdmin.search_service.entity.Tariff;
import com.tmAdmin.search_service.entity.TouristCompany;
import com.tmAdmin.search_service.exception.CustomException;
import com.tmAdmin.search_service.repository.TariffRepository;
import com.tmAdmin.search_service.repository.TouristCompanyRepository;
import com.tmAdmin.search_service.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private TouristCompanyRepository touristCompanyRepository;

    @Autowired
    private TariffRepository tariffRepository;

    @Override
    public List<TouristCompanyDto> searchByBranchId(Long branchId) {
        TouristCompany company = touristCompanyRepository.findById(branchId)
                .orElseThrow(() -> new CustomException("Branch ID not found"));
        return List.of(convertToResponseDto(company));
    }

    @Override
    public List<TouristCompanyDto> searchByBranchName(String branchName) {
        List<TouristCompany> companies = touristCompanyRepository.findByBranchNameContainingIgnoreCase(branchName);
        return companies.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TouristCompanyDto> searchByPlace(String place) {
        if (!List.of("ANDAMAN", "THAILAND", "DUBAI", "SINGAPORE", "MALAYSIA").contains(place.toUpperCase())) {
            throw new CustomException("Invalid place");
        }
        List<Tariff> tariffs = tariffRepository.findByPlaceOrderByTariffAmountDesc(place);
        return tariffs.stream()
                .collect(Collectors.groupingBy(Tariff::getTouristCompany))
                .entrySet().stream()
                .map(entry -> {
                    TouristCompanyDto companyDto = convertToResponseDto(entry.getKey());
                    List<TariffDto> tariffDtos = entry.getValue().stream().map(this::convertToTariffDto).collect(Collectors.toList());
                    companyDto.setTariffs(tariffDtos);
                    return companyDto;
                }).collect(Collectors.toList());
    }

    private TouristCompanyDto convertToResponseDto(TouristCompany company) {
        TouristCompanyDto dto = new TouristCompanyDto();
        dto.setBranchId(company.getBranchId());
        dto.setBranchName(company.getBranchName());
        dto.setPlace(company.getPlace());
        dto.setWebsite(company.getWebsite());
        dto.setContact(company.getContact());
        dto.setEmail(company.getEmail());
        List<TariffDto> tariffDtos = company.getTariffs().stream().map(this::convertToTariffDto).collect(Collectors.toList());
        dto.setTariffs(tariffDtos);
        return dto;
    }

    private TariffDto convertToTariffDto(Tariff tariff) {
        TariffDto dto = new TariffDto();
        dto.setTariffId(tariff.getTariffId());
        dto.setPlace(tariff.getPlace());
        dto.setTariffAmount(tariff.getTariffAmount());
        return dto;
    }

}
