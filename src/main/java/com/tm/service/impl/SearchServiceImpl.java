package com.tm.service.impl;

import com.tm.dto.TariffDto;
import com.tm.dto.TouristCompanyDto;
import com.tm.entity.Tariff;
import com.tm.entity.TouristCompany;
import com.tm.service.SearchService;
import com.tm.dto.TouristCompanyEvent;
import com.tm.exception.CustomException;
import com.tm.repository.TariffRepository;
import com.tm.repository.TouristCompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private TouristCompanyRepository touristCompanyRepository;

    @Autowired
    private TariffRepository tariffRepository;

    @KafkaListener(topics = "tourist-company-topic" , groupId = "group_id")
    private void processTourismCompanyEvent(TouristCompanyEvent event) {
        if(event.getEventType().contains("AddTourismCompany")) {
            touristCompanyRepository.save(event.getTouristCompany());
        } else if(event.getEventType().contains("UpdateTariff")) {
            updateTariff(event.getTouristCompany().getBranchId(), event.getTouristCompany().getTariffs().stream().map(this::convertToTariffDto).collect(Collectors.toList()));
        }
    }

    private TouristCompany findCompanyById(Long branchId) {
        Optional<TouristCompany> companyOpt = touristCompanyRepository.findById(branchId);
        System.out.println("Company found: " + companyOpt.isPresent());
        if (!companyOpt.isPresent()) {
            throw new CustomException("Invalid Branch ID");
        }
        return companyOpt.get();
    }

    public List<Tariff> updateTariffs(Long branchId, List<TariffDto> tariffDTOs, TouristCompany company) {
        return tariffDTOs.stream().map(tariffDTO -> {
            Optional<Tariff> existingTariffOpt = tariffRepository.findByPlaceAndTouristCompany_BranchId(tariffDTO.getPlace(), branchId);
            Tariff tariff;
            if (existingTariffOpt.isPresent()) {
                tariff = existingTariffOpt.get();
                tariff.setPlace(tariffDTO.getPlace());
                tariff.setTariffAmount(tariffDTO.getTariffAmount());
            } else {
                tariff = convertToEntity(tariffDTO);
                tariff.setTouristCompany(company);
            }
            return tariff;
        }).collect(Collectors.toList());
    }

    private Tariff convertToEntity(TariffDto tariffDTO) {
        Tariff tariff = new Tariff();
        tariff.setPlace(tariffDTO.getPlace());
        tariff.setTariffAmount(tariffDTO.getTariffAmount());
        return tariff;
    }

    @Transactional
    public void updateTariff(Long branchId, List<TariffDto> tariffDTOs) {
        TouristCompany company = findCompanyById(branchId);
        List<Tariff> existingTariffs = company.getTariffs();
        List<Tariff> updatedTariffs = updateTariffs(branchId, tariffDTOs, company);
        deleteRemovedTariffs(existingTariffs, updatedTariffs);
        company.getTariffs().clear();
        company.getTariffs().addAll(updatedTariffs);
        touristCompanyRepository.save(company);
    }

    public void deleteRemovedTariffs(List<Tariff> existingTariffs, List<Tariff> updatedTariffs) {
        List<Long> updatedTariffIds = updatedTariffs.stream().map(Tariff::getTariffId).collect(Collectors.toList());
        List<Tariff> tariffsToDelete = existingTariffs.stream()
                .filter(tariff -> !updatedTariffIds.contains(tariff.getTariffId()))
                .collect(Collectors.toList());
        tariffRepository.deleteAll(tariffsToDelete);
    }

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
