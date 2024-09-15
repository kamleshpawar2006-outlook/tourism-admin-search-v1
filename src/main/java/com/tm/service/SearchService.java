package com.tm.service;

import com.tm.dto.TouristCompanyDto;

import java.util.List;

public interface SearchService {
    List<TouristCompanyDto> searchByBranchId(Long branchId);
    List<TouristCompanyDto> searchByBranchName(String branchName);
    List<TouristCompanyDto> searchByPlace(String place);
}
