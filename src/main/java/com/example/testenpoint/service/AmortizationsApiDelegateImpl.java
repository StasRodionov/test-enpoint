package com.example.testenpoint.service;

import com.example.testenpoint.model.AmortizationEntity;
import com.example.testenpoint.repository.AmortizationRepository;
import com.rzd.task.api.AmortizationsApiDelegate;
import com.rzd.task.dto.AmortizationDto;
import com.rzd.task.dto.AmortizationFilterDto;
import com.rzd.task.dto.AmortizationViewDto;
import jakarta.transaction.Transactional;
import jline.internal.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class AmortizationsApiDelegateImpl implements AmortizationsApiDelegate {
    private final AmortizationRepository amortizationRepository;

    @Autowired
    public AmortizationsApiDelegateImpl(AmortizationRepository amortizationRepository) {
        this.amortizationRepository = amortizationRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<AmortizationViewDto> addAmortization(AmortizationDto amortizationDto) {
        AmortizationEntity entity = convertToEntity(amortizationDto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        String timestamp = LocalDateTime.now().format(formatter);
        Log.info(timestamp + " " + this.getClass() + " Сохранение в БД " + entity);
        amortizationRepository.save(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AmortizationViewDto>> getAmortizations(AmortizationFilterDto filter, Pageable page) {
        UUID vehicleKindUuid = filter.getVehicleKindUuid();
        Double avgPrice = filter.getAvgPrice();
        Integer usePeriod = filter.getUsePeriod();

        Page<AmortizationEntity> amortizationEntities;

        if (vehicleKindUuid != null && avgPrice != null && usePeriod != null) {
            amortizationEntities = amortizationRepository.findByVehicleKindUuidAndAvgPriceAndUsePeriod(vehicleKindUuid, avgPrice, usePeriod, page);
        } else if (vehicleKindUuid != null && avgPrice != null) {
            amortizationEntities = amortizationRepository.findByVehicleKindUuidAndAvgPrice(vehicleKindUuid, avgPrice, page);
        } else if (vehicleKindUuid != null && usePeriod != null) {
            amortizationEntities = amortizationRepository.findByVehicleKindUuidAndUsePeriod(vehicleKindUuid, usePeriod, page);
        } else if (avgPrice != null && usePeriod != null) {
            amortizationEntities = amortizationRepository.findByAvgPriceAndUsePeriod(avgPrice, usePeriod, page);
        } else if (vehicleKindUuid != null) {
            amortizationEntities = amortizationRepository.findByVehicleKindUuid(vehicleKindUuid, page);
        } else if (avgPrice != null) {
            amortizationEntities = amortizationRepository.findByAvgPrice(avgPrice, page);
        } else if (usePeriod != null) {
            amortizationEntities = amortizationRepository.findByUsePeriod(usePeriod, page);
        } else {
            amortizationEntities = amortizationRepository.findAll(page);
        }

        List<AmortizationViewDto> amortizationViewDtos = convertToAmortizationViewDtos(amortizationEntities.getContent());
        return new ResponseEntity<>(amortizationViewDtos, HttpStatus.OK);
    }

    private AmortizationEntity convertToEntity(AmortizationDto amortizationDto) {
        AmortizationEntity entity = new AmortizationEntity();

        if (amortizationDto.getVehicleKindUuid() != null) {
            entity.setVehicleKindUuid(amortizationDto.getVehicleKindUuid());
        }
        if (amortizationDto.getUsePeriod() != null) {
            entity.setUsePeriod(amortizationDto.getUsePeriod());
        }
        if (amortizationDto.getAvgPrice() != null) {
            entity.setAvgPrice(amortizationDto.getAvgPrice());
        }
        if (amortizationDto.getAvgPrice() != null && amortizationDto.getUsePeriod() != null) {
            entity.setTotalPrice(amortizationDto.getAvgPrice() * amortizationDto.getUsePeriod());
        }
        return entity;
    }

    private List<AmortizationViewDto> convertToAmortizationViewDtos(List<AmortizationEntity> amortizationEntities) {
        List<AmortizationViewDto> amortizationViewDtos = new ArrayList<>();
        for (AmortizationEntity entity : amortizationEntities) {
            AmortizationViewDto viewDto = convertToAmortizationViewDto(entity);
            amortizationViewDtos.add(viewDto);
        }
        return amortizationViewDtos;
    }

    private AmortizationViewDto convertToAmortizationViewDto(AmortizationEntity amortizationEntity) {
        AmortizationViewDto viewDto = new AmortizationViewDto();
        viewDto.setUuid(amortizationEntity.getUuid());
        viewDto.setVehicleKindUuid(amortizationEntity.getVehicleKindUuid());
        viewDto.setUsePeriod(amortizationEntity.getUsePeriod());
        viewDto.setAvgPrice(amortizationEntity.getAvgPrice());
        viewDto.setTotalPrice(amortizationEntity.getTotalPrice());
        return viewDto;
    }
}

