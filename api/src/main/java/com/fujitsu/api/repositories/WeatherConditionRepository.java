package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {


    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.name = :stationName " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findLatestByStationName(@Param(value = "stationName") String stationName);

    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.city = :cityName " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findLatestByCityName(@Param(value = "cityName") String cityName);

    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.name = :stationName AND wc.timestamp <= :localDateTime " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findByStationNameAtSpecifiedDate(
            @Param(value = "stationName") String stationName, @Param(value = "localDateTime") LocalDateTime localDateTime);

    @Query("FROM WeatherCondition wc " +
            "WHERE wc.station.city = :cityName AND wc.timestamp <= :localDateTime " +
            "ORDER BY wc.timestamp DESC " +
            "LIMIT 1")
    Optional<WeatherCondition> findByCityNameAtSpecifiedDate(
            @Param(value = "cityName") String cityName, @Param(value = "localDateTime") LocalDateTime localDateTime);
}
