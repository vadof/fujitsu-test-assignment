package com.fujitsu.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regional_base_fee", uniqueConstraints = {@UniqueConstraint(columnNames = {"city", "vehicle_type"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionalBaseFee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "fee", nullable = false)
    private Double fee;
}
