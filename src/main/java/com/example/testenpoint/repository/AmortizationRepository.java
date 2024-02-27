package com.example.testenpoint.repository;

import com.example.testenpoint.model.AmortizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AmortizationRepository extends JpaRepository<AmortizationEntity, UUID> {
    Page<AmortizationEntity> findByVehicleKindUuidAndAvgPriceAndUsePeriod(UUID vehicleKindUuid, Double avgPrice, Integer usePeriod, Pageable pageable);

    Page<AmortizationEntity> findByVehicleKindUuidAndAvgPrice(UUID vehicleKindUuid, Double avgPrice, Pageable pageable);

    Page<AmortizationEntity> findByUsePeriod(Integer usePeriod, Pageable page);

    Page<AmortizationEntity> findByVehicleKindUuidAndUsePeriod(UUID vehicleKindUuid, Integer usePeriod, Pageable page);

    Page<AmortizationEntity> findByAvgPriceAndUsePeriod(Double avgPrice, Integer usePeriod, Pageable page);

    Page<AmortizationEntity> findByVehicleKindUuid(UUID vehicleKindUuid, Pageable page);

    Page<AmortizationEntity> findByAvgPrice(Double avgPrice, Pageable page);
}
