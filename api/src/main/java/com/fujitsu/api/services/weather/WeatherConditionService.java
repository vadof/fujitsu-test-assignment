package com.fujitsu.api.services.weather;

import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.repositories.WeatherConditionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherConditionService {

    private final WeatherConditionRepository weatherConditionRepository;

    public WeatherCondition save(WeatherCondition weatherCondition) {
        return weatherConditionRepository.save(weatherCondition);
    }

    public Optional<WeatherCondition> getLatestWeatherCondition(String stationName) {
        return weatherConditionRepository.findLatestByStationName(stationName);
    }

    public Optional<WeatherCondition> getLatestWeatherConditionByCity(String cityName) {
        return weatherConditionRepository.findLatestByCityName(cityName);
    }

    public Optional<WeatherCondition> getWeatherConditionByCityAndDate(String cityName, LocalDateTime dateTime) {
        return weatherConditionRepository.findByCityNameAtSpecifiedDate(cityName, dateTime);
    }

}
