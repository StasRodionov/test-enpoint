package com.example.testenpoint.controller;
import com.example.testenpoint.service.AmortizationsApiDelegateImpl;
import com.rzd.task.api.AmortizationsApiDelegate;
import com.rzd.task.dto.AmortizationDto;
import com.rzd.task.dto.AmortizationFilterDto;
import com.rzd.task.dto.AmortizationViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AmortizationController {

    private final AmortizationsApiDelegate amortizationsApiDelegate;


    @Autowired
    public AmortizationController(AmortizationsApiDelegate amortizationsApiDelegate) {
        this.amortizationsApiDelegate= amortizationsApiDelegate;
    }

    @PostMapping("/amortizations")
    public ResponseEntity<AmortizationDto> addAmortizationDto(@RequestBody AmortizationDto amortizationDto) {
        amortizationsApiDelegate.addAmortization(amortizationDto);
        return new ResponseEntity<>(amortizationDto, HttpStatus.OK);
    }

    @GetMapping("/amortization")
    public ResponseEntity<List<AmortizationViewDto>> getAmortizationViewDto(AmortizationFilterDto filter, Pageable page) {
        List<AmortizationViewDto> amortizationViewDtos = amortizationsApiDelegate.getAmortizations(filter, PageRequest.of(page.getPageNumber(), page.getPageSize())).getBody();
        return new ResponseEntity<>(amortizationViewDtos, HttpStatus.OK);
    }
}