package com.fujitsu.api.services;

import com.fujitsu.api.config.Constants;
import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.repositories.WeatherConditionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherConditionService {

    private final WeatherConditionRepository weatherConditionRepository;
    private final Map<String, WeatherCondition> latestData = new HashMap<>();

    public WeatherCondition save(WeatherCondition weatherCondition) {
        WeatherCondition saved = weatherConditionRepository.save(weatherCondition);
        latestData.put(weatherCondition.getStationName(), saved);
        return saved;
    }

    public Optional<WeatherCondition> getLatestWeatherCondition(String stationName) {
        WeatherCondition weatherCondition = latestData.get(stationName);
        if (weatherCondition == null) {
            return weatherConditionRepository.findByStationName(stationName);
        }
        return Optional.of(weatherCondition);
    }

    public Optional<WeatherCondition> getLatestWeatherConditionByCity(String cityName) {
        List<String> cityNames = Constants.CITY_NAMES;
        for (int i = 0; i < cityNames.size(); i++) {
            if (cityNames.get(i).equalsIgnoreCase(cityName)) {
                return getLatestWeatherCondition(Constants.STATION_NAMES.get(i));
            }
        }
        return Optional.empty();
    }

}
