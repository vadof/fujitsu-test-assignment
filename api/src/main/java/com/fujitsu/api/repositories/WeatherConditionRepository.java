package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {


    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.name = :stationName " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findLatestByStationName(String stationName);

    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.city = :cityName " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findLatestByCityName(String cityName);

    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.name = :stationName AND wc.timestamp <= :localDateTime " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findByStationNameAtSpecifiedDate(String stationName, LocalDateTime localDateTime);

    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.city = :cityName AND wc.timestamp <= :localDateTime " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findByCityNameAtSpecifiedDate(String cityName, LocalDateTime localDateTime);
}
