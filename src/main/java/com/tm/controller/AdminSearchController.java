package com.tm.controller;

import com.tm.dto.TouristCompanyDto;
import com.tm.exception.CustomException;
import com.tm.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tourism/api/v1/admin")
@Validated
public class AdminSearchController {

    @Autowired
    private SearchService searchService;


    @Operation(summary = "Search for tourist companies by different criteria",
            description = "Search based on branchId, branchName, or place.")
    @GetMapping("/{criteria}/{criteriaValue}")
    public List<TouristCompanyDto> search(@Parameter(description = "The search criteria (branchId, branchName, place)", required = true)  @PathVariable String criteria, @Parameter(description = "The value for the search criteria", required = true) @PathVariable String criteriaValue) {
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