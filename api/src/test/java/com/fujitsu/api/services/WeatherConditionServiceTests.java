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
    @DisplayName("Get latest WeatherCondition by station name")
    void getLatestWeatherConditionByStationName() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);
        String name = weatherCondition.getStation().getName();

        when(repository.findLatestByStationName(name))
                .thenReturn(Optional.of(weatherCondition));

        Optional<WeatherCondition> latest = service.getLatestWeatherCondition(name);

        assertTrue(latest.isPresent());
        assertThat(latest.get().getAirTemperature()).isEqualTo(weatherCondition.getAirTemperature());
    }

    @Test
    @DisplayName("Get latest WeatherCondition by city name")
    void getLatestWeatherConditionByCity() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);
        String name = weatherCondition.getStation().getCity();

        when(repository.findLatestByCityName(name))
                .thenReturn(Optional.of(weatherCondition));

        Optional<WeatherCondition> latest = service.getLatestWeatherConditionByCity(name);

        assertTrue(latest.isPresent());
        assertThat(latest.get().getAirTemperature()).isEqualTo(weatherCondition.getAirTemperature());
    }

    @Test
    @DisplayName("Get latest WeatherCondition by wrong city name")
    void getLatestWeatherConditionByWrongCity() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);
        String name = weatherCondition.getStation().getCity();

        when(repository.findLatestByCityName(name))
                .thenReturn(Optional.of(weatherCondition));

        Optional<WeatherCondition> latest = service.getLatestWeatherConditionByCity("Rakvere");

        assertTrue(latest.isEmpty());
    }

    @Test
    @DisplayName("Get WeatherCondition by city name at specified date")
    void getWeatherConditionByCityAndDate() {
        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(null);
        String name = weatherCondition.getStation().getCity();

        LocalDateTime date = weatherCondition.getTimestamp().toLocalDateTime().plusHours(1);

        when(repository.findByCityNameAtSpecifiedDate(name, date))
                .thenReturn(Optional.of(weatherCondition));

        Optional<WeatherCondition> result = service.getWeatherConditionByCityAndDate(name, date);

        assertTrue(result.isPresent());
        assertThat(result.get().getTimestamp()).isEqualTo(weatherCondition.getTimestamp());
    }

}
