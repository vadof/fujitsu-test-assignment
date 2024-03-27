package com.fujitsu.api.services;

import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.mocks.WeatherConditionMock;
import com.fujitsu.api.repositories.WeatherConditionRepository;
import com.fujitsu.api.services.weather.WeatherConditionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherConditionServiceTests {

    @Mock
    private WeatherConditionRepository repository;

    @InjectMocks
    private WeatherConditionService service;

    @Test
    @DisplayName("Save WeatherCondition")
    void saveWeatherCondition() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);
        service.save(weatherCondition);
        verify(repository, times(1)).save(weatherCondition);
    }

    @Test
    @DisplayName("Save WeatherCondition and then get it")
    void saveAndGetLatestWeatherCondition() {
        WeatherCondition weatherCondition1 = WeatherConditionMock.getWeatherConditionMock(null);
        WeatherCondition weatherCondition2 = WeatherConditionMock.getWeatherConditionMock(null);
        weatherCondition2.setAirTemperature(100d);

        when(repository.save(weatherCondition1)).thenReturn(weatherCondition1);
        when(repository.save(weatherCondition2)).thenReturn(weatherCondition2);

        service.save(weatherCondition1);
        service.save(weatherCondition2);

        Optional<WeatherCondition> latest = service.getLatestWeatherCondition(weatherCondition1.getStationName());

        assertTrue(latest.isPresent());
        assertThat(latest.get().getAirTemperature()).isEqualTo(weatherCondition2.getAirTemperature());
    }

    @Test
    @DisplayName("Save WeatherCondition and then get it by city name")
    void saveAndGetLatestWeatherConditionByCitySuccess() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);

        when(repository.save(weatherCondition)).thenReturn(weatherCondition);

        service.save(weatherCondition);

        Optional<WeatherCondition> latest = service.getLatestWeatherConditionByCity("Tallinn");

        assertTrue(latest.isPresent());
        assertThat(latest.get().getStationName()).isEqualTo(weatherCondition.getStationName());
    }

    @Test
    @DisplayName("Save WeatherCondition and then get it by wrong city name")
    void saveAndGetLatestWeatherConditionByWrongCity() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);

        when(repository.save(weatherCondition)).thenReturn(weatherCondition);

        service.save(weatherCondition);

        Optional<WeatherCondition> latest = service.getLatestWeatherConditionByCity("Rakvere");

        assertTrue(latest.isEmpty());
    }

    @Test
    @DisplayName("Get WeatherCondition by city name at specified date")
    void getWeatherConditionByCityAndDate() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);
        service.save(weatherCondition);

        LocalDateTime date = weatherCondition.getTimestamp().toLocalDateTime().plusHours(1);

        when(repository.findByStationNameAtSpecifiedDate(weatherCondition.getStationName(), date))
                .thenReturn(Optional.of(weatherCondition));

        Optional<WeatherCondition> result = service.getWeatherConditionByCityAndDate("Tallinn", date);

        assertTrue(result.isPresent());
        assertThat(result.get().getTimestamp()).isEqualTo(weatherCondition.getTimestamp());
    }

}
