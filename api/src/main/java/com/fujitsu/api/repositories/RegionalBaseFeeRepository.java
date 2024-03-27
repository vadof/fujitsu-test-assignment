package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFee, Long> {
    Optional<RegionalBaseFee> findByCityAndVehicleType(String city, String vehicleType);
    Boolean existsByCity(String city);
}
