package com.fujitsu.api.schedulers;

import com.fujitsu.api.services.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class WeatherScheduler {

    private final WeatherService weatherService;

    @Scheduled(cron = "0 15 * * * *")
    public void updateWeatherData() {
        log.debug("Weather data update has started");
        weatherService.updateWeatherData();
    }

}
