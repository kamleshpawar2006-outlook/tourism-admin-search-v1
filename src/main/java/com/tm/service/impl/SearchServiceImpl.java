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
import lombok.extern.slf4j.Slf4j;
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
    public void processTourismCompanyEvent(TouristCompanyEvent event) {
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
                .collect(Collectors.groupingBy(Tariff::getTouristCompany))  // Group tariffs by TouristCompany
                .entrySet().stream()
                .map(entry -> {
                    List<TariffDto> tariffDtos = entry.getValue().stream()
                            .map(this::convertToTariffDto)
                            .collect(Collectors.toList());

                    return new TouristCompanyDto.Builder()
                            .withBranchId(entry.getKey().getBranchId())     // Set Branch ID
                            .withBranchName(entry.getKey().getBranchName()) // Set Branch Name
                            .withPlace(entry.getKey().getPlace())           // Set Place
                            .withWebsite(entry.getKey().getWebsite())       // Set Website
                            .withContact(entry.getKey().getContact())       // Set Contact
                            .withEmail(entry.getKey().getEmail())           // Set Email
                            .withTariffs(tariffDtos)                        // Set the List of TariffDtos
                            .build();
                })
                .collect(Collectors.toList());
    }

    private TouristCompanyDto convertToResponseDto(TouristCompany company) {
        List<TariffDto> tariffDtos = company.getTariffs().stream()
                .map(this::convertToTariffDto)
                .collect(Collectors.toList());

        TouristCompanyDto dto = new TouristCompanyDto.Builder()
                .withBranchId(company.getBranchId())
                .withBranchName(company.getBranchName())
                .withPlace(company.getPlace())
                .withWebsite(company.getWebsite())
                .withContact(company.getContact())
                .withEmail(company.getEmail())
                .withTariffs(tariffDtos)
                .build();
        return dto;
    }

    private TariffDto convertToTariffDto(Tariff tariff) {
        TariffDto dto = new TariffDto.Builder()
                .withTariffId(tariff.getTariffId())
                .withPlace(tariff.getPlace())
                .withTariffAmount(tariff.getTariffAmount())
                .build();
        return dto;
    }

}
