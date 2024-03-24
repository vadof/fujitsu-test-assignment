package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {
}
