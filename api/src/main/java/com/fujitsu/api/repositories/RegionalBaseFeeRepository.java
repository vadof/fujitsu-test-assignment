package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFee, Long> {
    Optional<RegionalBaseFee> findByCityAndVehicleType(String city, String vehicleType);
    Boolean existsByCity(String city);

    @Query("SELECT r.city, GROUP_CONCAT(r.vehicleType) FROM RegionalBaseFee r GROUP BY r.city")
    List<String[]> getCityToVehicleTypeList();
}
