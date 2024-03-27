package com.fujitsu.api.services;

import com.fujitsu.api.entities.RegionalBaseFee;
import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.exceptions.AppException;
import com.fujitsu.api.mocks.RegionalBaseFeeMock;
import com.fujitsu.api.mocks.WeatherConditionMock;
import com.fujitsu.api.services.weather.WeatherConditionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class DeliveryFeeCalculatorTests {
    @Mock
    private WeatherConditionService weatherConditionService;

    @Mock
    private RegionalBaseFeeService rbfService;

    @InjectMocks
    private DeliveryFeeCalculatorImpl calculator;

    @Test
    @DisplayName("Calculate RBF")
    public void calculateRBF() {
        String city = "Tallinn";
        String vehicle = "Bike";

        RegionalBaseFee rbf = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);
        when(rbfService.getRegionalBaseFee(city, vehicle)).thenReturn(rbf);

        Double fee = calculator.calculateRBF(city, vehicle);

        assertThat(fee).isEqualTo(rbf.getFee());
    }

    @Test
    @DisplayName("Calculate ATEF using Car")
    public void calculateATEFCar() {
        String vehicle = "Car";

        Double fee;
        for (double i = -20d; i <= 20d; i += 5) {
            fee = calculator.calculateATEF(i, vehicle);
            assertThat(fee).isEqualTo(0d);
        }
    }

    @Test
    @DisplayName("Calculate ATEF using Scooter")
    public void calculateATEFScooter() {
        String vehicle = "Scooter";

        List<Double> testValues = List.of(-20d, -13.5d, -10.1d, -10d, 0d, 1d, 25d);
        List<Double> returnValues = List.of(1d, 1d, 1d, 0.5d, 0.5d, 0d, 0d);

        Double fee;
        for (int i = 0; i < testValues.size(); i++) {
            fee = calculator.calculateATEF(testValues.get(i), vehicle);
            assertThat(fee).isEqualTo(returnValues.get(i));
        }
    }

    @Test
    @DisplayName("Calculate ATEF using Bike")
    public void calculateATEFBike() {
        String vehicle = "Bike";

        List<Double> testValues = List.of(-20d, -13.5d, -10.1d, -10d, 0d, 1d, 25d);
        List<Double> returnValues = List.of(1d, 1d, 1d, 0.5d, 0.5d, 0d, 0d);

        Double fee;
        for (int i = 0; i < testValues.size(); i++) {
            fee = calculator.calculateATEF(testValues.get(i), vehicle);
            assertThat(fee).isEqualTo(returnValues.get(i));
        }
    }

    @Test
    @DisplayName("Calculate WSEF using Car")
    public void calculateWSEFCar() {
        String vehicle = "Car";

        Double fee;
        for (double i = -20d; i <= 20d; i += 5) {
            fee = calculator.calculateWSEF(i, vehicle);
            assertThat(fee).isEqualTo(0d);
        }
    }

    @Test
    @DisplayName("Calculate WSEF using Scooter")
    public void calculateWSEFScooter() {
        String vehicle = "Scooter";

        Double fee;
        for (double i = -20d; i <= 20d; i += 5) {
            fee = calculator.calculateWSEF(i, vehicle);
            assertThat(fee).isEqualTo(0d);
        }
    }

    @Test
    @DisplayName("Calculate WSEF using Bike")
    public void calculateWSEFBike() {
        String vehicle = "Bike";

        List<Double> testValues = List.of(0d, 5d, 9.9d, 10d, 10d, 15.3d, 20d);
        List<Double> returnValues = List.of(0d, 0d, 0d, 0.5d, 0.5d, 0.5d, 0.5d);

        Double fee;
        for (int i = 0; i < testValues.size(); i++) {
            fee = calculator.calculateWSEF(testValues.get(i), vehicle);
            assertThat(fee).isEqualTo(returnValues.get(i));
        }

        assertThrows(AppException.class, () -> calculator.calculateWSEF(20.1d, vehicle));
    }

    @Test
    @DisplayName("Calculate WPEF using Car")
    public void calculateWPEFCar() {
        String vehicle = "Car";

        List<String> testValues = List.of("Thunderstorm", "Glaze", "Heavy snowfall", "Heavy rain");

        Double fee;
        for (String phenomenon : testValues) {
            fee = calculator.calculateWPEF(phenomenon, vehicle);
            assertThat(fee).isEqualTo(0d);
        }
    }

    @Test
    @DisplayName("Calculate WPEF using Scooter")
    public void calculateWPEFScooter() {
        String vehicle = "Scooter";

        List<String> testValues = List.of("Light sleet", "Heavy snowfall", "Heavy rain");
        List<Double> returnValues = List.of(1d, 1d, 0.5d);

        Double fee;
        for (int i = 0; i < testValues.size(); i++) {
            fee = calculator.calculateWPEF(testValues.get(i), vehicle);
            assertThat(fee).isEqualTo(returnValues.get(i));
        }

        assertThrows(AppException.class, () -> calculator.calculateWPEF("Thunderstorm", vehicle));
    }

    @Test
    @DisplayName("Calculate WPEF using Bike")
    public void calculateWPEFBike() {
        String vehicle = "Bike";

        List<String> testValues = List.of("Light sleet", "Heavy snowfall", "Heavy rain");
        List<Double> returnValues = List.of(1d, 1d, 0.5d);

        Double fee;
        for (int i = 0; i < testValues.size(); i++) {
            fee = calculator.calculateWPEF(testValues.get(i), vehicle);
            assertThat(fee).isEqualTo(returnValues.get(i));
        }

        assertThrows(AppException.class, () -> calculator.calculateWPEF("Thunderstorm", vehicle));
    }

    @Test
    @DisplayName("Calculate Weather fees")
    void calculateWeatherFees() {
        String vehicle = "Bike";

        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(1L);
        weatherCondition.setAirTemperature(-5d);
        weatherCondition.setWeatherPhenomenon("Light rain");
        weatherCondition.setWindSpeed(1d);

        Double fee = calculator.calculateWeatherFees(weatherCondition, vehicle);

        assertThat(fee).isEqualTo(1d);
    }

    @Test
    @DisplayName("Calculate Delivery fee")
    public void calculateDeliveryFee() {
        String city = "Tallinn";
        String vehicle = "Bike";

        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(1L);
        weatherCondition.setAirTemperature(-5d);
        weatherCondition.setWeatherPhenomenon("Light rain");
        weatherCondition.setWindSpeed(1d);

        when(weatherConditionService.getLatestWeatherConditionByCity(city))
                .thenReturn(Optional.of(weatherCondition));

        RegionalBaseFee rbf = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);
        when(rbfService.getRegionalBaseFee(city, vehicle)).thenReturn(rbf);

        Double fee = calculator.calculateDeliveryFee(city, vehicle, null);

        assertThat(fee).isEqualTo(5d);
    }

    @Test
    @DisplayName("Calculate Delivery fee at specified date")
    public void calculateDeliveryFeeAtDate() {
        String city = "Tallinn";
        String vehicle = "Bike";
        LocalDateTime date = LocalDateTime.of(2023, 12, 12, 12, 1, 0);

        WeatherCondition weatherCondition = WeatherConditionMock.getWeatherConditionMock(1L);
        weatherCondition.setAirTemperature(-5d);
        weatherCondition.setWeatherPhenomenon("Light rain");
        weatherCondition.setWindSpeed(1d);

        when(weatherConditionService.getWeatherConditionByCityAndDate(city, date))
                .thenReturn(Optional.of(weatherCondition));

        RegionalBaseFee rbf = RegionalBaseFeeMock.getRegionalBaseFeeMock(1L);
        when(rbfService.getRegionalBaseFee(city, vehicle)).thenReturn(rbf);

        Double fee = calculator.calculateDeliveryFee(city, vehicle, date);

        assertThat(fee).isEqualTo(5d);
    }


}
