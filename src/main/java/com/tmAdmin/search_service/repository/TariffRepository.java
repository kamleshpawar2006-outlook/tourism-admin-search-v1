package com.tmAdmin.search_service.repository;

import com.tmAdmin.search_service.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByPlaceAndTouristCompany_BranchId(String place, Long branchId);
    List<Tariff> findByPlaceOrderByTariffAmountDesc(String place);
}
