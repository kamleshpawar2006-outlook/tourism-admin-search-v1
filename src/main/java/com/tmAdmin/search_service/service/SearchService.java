package com.tmAdmin.search_service.service;

import com.tmAdmin.search_service.dto.TouristCompanyDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchService {
    List<TouristCompanyDto> searchByBranchId(Long branchId);
    List<TouristCompanyDto> searchByBranchName(String branchName);
    List<TouristCompanyDto> searchByPlace(String place);
}
