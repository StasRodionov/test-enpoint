package com.example.testenpoint.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class AmortizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private UUID vehicleKindUuid;
    private Integer usePeriod;
    private Double avgPrice;
    private Double totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmortizationEntity that = (AmortizationEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(vehicleKindUuid, that.vehicleKindUuid) && Objects.equals(usePeriod, that.usePeriod) && Objects.equals(avgPrice, that.avgPrice) && Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, vehicleKindUuid, usePeriod, avgPrice, totalPrice);
    }
}
