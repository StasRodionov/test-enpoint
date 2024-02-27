package com.example.testenpoint.util;

import com.rzd.task.api.AmortizationsApiDelegate;
import com.rzd.task.dto.AmortizationDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class InitBD {
    private final AmortizationsApiDelegate amortizationsApiDelegate;

    public InitBD(AmortizationsApiDelegate amortizationsApiDelegate) {
        this.amortizationsApiDelegate = amortizationsApiDelegate;
    }

    @PostConstruct
    public void fillBD() {

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            AmortizationDto amortizationDto = new AmortizationDto();
            amortizationDto.setUsePeriod(i % 10);
            amortizationDto.setAvgPrice(random.nextDouble(1000, 5000));
            amortizationDto.setVehicleKindUuid(UUID.randomUUID());
            amortizationsApiDelegate.addAmortization(amortizationDto);
        }
    }
}
