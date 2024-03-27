package com.fujitsu.api.mocks;

import com.fujitsu.api.entities.WeatherCondition;

import java.sql.Timestamp;

public class WeatherConditionMock {
    public static WeatherCondition getWeatherConditionMock(Long id) {
        return WeatherCondition.builder()
                .id(id)
                .weatherPhenomenon("Overcast")
                .wmoCode(0)
                .stationName("Tallinn-Harku")
                .airTemperature(3d)
                .windSpeed(4d)
                .timestamp(new Timestamp(2024, 3, 27, 10, 15, 0, 0))
                .build();
    }
}
