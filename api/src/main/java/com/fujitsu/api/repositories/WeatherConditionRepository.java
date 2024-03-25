package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {
    Optional<WeatherCondition> findByStationName(String stationName);
}
