package com.fujitsu.api.config;

import com.fujitsu.api.services.weather.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseInitializer {
    private final WeatherService weatherService;

    @Bean
    public void updateWeatherData() {
        weatherService.updateWeatherData();
    }

}
