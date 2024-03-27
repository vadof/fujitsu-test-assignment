package com.fujitsu.api.services;

import com.fujitsu.api.config.Constants;
import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.repositories.WeatherConditionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            return weatherConditionRepository.findLatestByStationName(stationName);
        }
        return Optional.of(weatherCondition);
    }

    public Optional<WeatherCondition> getLatestWeatherConditionByCity(String cityName) {
        Optional<String> optionalCityName = getStationNameByCityName(cityName);
        if (optionalCityName.isPresent()) {
            return getLatestWeatherCondition(optionalCityName.get());
        }

        return Optional.empty();
    }

    public Optional<WeatherCondition> getWeatherConditionByCityAndDate(String cityName, LocalDateTime dateTime) {
        Optional<String> stationName = getStationNameByCityName(cityName);
        if (stationName.isPresent()) {
            return weatherConditionRepository.findByStationNameAtSpecifiedDate(stationName.get(), dateTime);
        }
        return Optional.empty();
    }

    private Optional<String> getStationNameByCityName(String cityName) {
        List<String> cityNames = Constants.CITY_NAMES;
        for (int i = 0; i < cityNames.size(); i++) {
            if (cityNames.get(i).equals(cityName)) {
                return Optional.of(Constants.STATION_NAMES.get(i));
            }
        }
        return Optional.empty();
    }
}
