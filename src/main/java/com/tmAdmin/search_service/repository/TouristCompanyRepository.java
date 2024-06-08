package com.tmAdmin.search_service.repository;

import com.tmAdmin.search_service.entity.TouristCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristCompanyRepository extends JpaRepository<TouristCompany, Long> {
    List<TouristCompany> findByBranchNameContainingIgnoreCase(String branchName);
}
