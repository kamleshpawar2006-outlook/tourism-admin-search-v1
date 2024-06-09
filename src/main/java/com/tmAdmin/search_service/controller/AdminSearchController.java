package com.tmAdmin.search_service.controller;

import com.tmAdmin.search_service.dto.TouristCompanyDto;
import com.tmAdmin.search_service.exception.CustomException;
import com.tmAdmin.search_service.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tourism/api/v1/admin")
@Validated
public class AdminSearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/{criteria}/{criteriaValue}")
    public List<TouristCompanyDto> search(@PathVariable String criteria, @PathVariable String criteriaValue) {
        switch (criteria) {
            case "branchId":
                return searchService.searchByBranchId(Long.valueOf(criteriaValue)); 
            case "branchName":
                return searchService.searchByBranchName(criteriaValue);
            case "place":
                return searchService.searchByPlace(criteriaValue);
            default:
                throw new CustomException("Invalid search criteria");
        }
    }
}
