package com.fujitsu.api.services.delivery;

import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.exceptions.AppException;
import com.fujitsu.api.services.RegionalBaseFeeService;
import com.fujitsu.api.services.weather.WeatherConditionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryFeeCalculatorImpl implements DeliveryFeeCalculator {

    private final WeatherConditionService weatherConditionService;
    private final RegionalBaseFeeService rbfService;

    public Double calculateDeliveryFee(String cityName, String vehicleType, LocalDateTime dateTime) {
        WeatherCondition weatherCondition = getWeatherCondition(cityName, dateTime);
        return calculateRBF(cityName, vehicleType) + calculateWeatherFees(weatherCondition, vehicleType);
    }

    private WeatherCondition getWeatherCondition(String cityName, LocalDateTime dateTime) {
        if (dateTime != null) {
            return weatherConditionService.getWeatherConditionByCityAndDate(cityName, dateTime).orElseThrow(
                    () -> new AppException(String.format("No weather data for %s at %s", cityName, dateTime),
                            HttpStatus.NOT_FOUND));
        } else {
            return weatherConditionService.getLatestWeatherConditionByCity(cityName).orElseThrow(
                    () -> new AppException("No weather data available for " + cityName, HttpStatus.NOT_FOUND));
        }
    }

    @Override
    public Double calculateRBF(String cityName, String vehicleType) {
        return rbfService.getRegionalBaseFee(cityName, vehicleType).getFee();
    }

    public Double calculateWeatherFees(WeatherCondition weatherCondition, String vehicleType) {
        if (vehicleType.equals("Car")) return 0d;

        Double fee = calculateATEF(weatherCondition.getAirTemperature(), vehicleType);
        fee += calculateWSEF(weatherCondition.getWindSpeed(), vehicleType);
        fee += calculateWPEF(weatherCondition.getWeatherPhenomenon(), vehicleType);

        return fee;
    }

    @Override
    public Double calculateATEF(Double airTemperature, String vehicleType) {
        if (vehicleType.equals("Car")) {
            return 0d;
        }

        if (airTemperature < -10) {
            return 1d;
        } else if (airTemperature <= 0) {
            return 0.5d;
        }
        return 0d;
    }

    @Override
    public Double calculateWSEF(Double windSpeed, String vehicleType) {
        if (!vehicleType.equals("Bike")) {
            return 0d;
        }

        if (windSpeed > 20) {
            throw new AppException("The use of the selected type of vehicle is prohibited due to weather conditions",
                    HttpStatus.FORBIDDEN);
        }

        if (windSpeed >= 10) {
            return 0.5;
        }

        return 0d;
    }

    @Override
    public Double calculateWPEF(String phenomenon, String vehicleType) {
        if (vehicleType.equals("Car")) {
            return 0d;
        }

        phenomenon = phenomenon.toLowerCase();

        if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
            return 1d;
        } else if (phenomenon.contains("rain")) {
            return 0.5;
        } else if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder")) {
            throw new AppException("The use of the selected type of vehicle is prohibited due to weather conditions",
                    HttpStatus.FORBIDDEN);
        }

        return 0d;
    }

}
