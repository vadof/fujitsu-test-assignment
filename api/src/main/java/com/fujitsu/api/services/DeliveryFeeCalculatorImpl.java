package com.fujitsu.api.services;

import com.fujitsu.api.config.Constants;
import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryFeeCalculatorImpl implements DeliveryFeeCalculator {

    private final WeatherConditionService weatherConditionService;

    public Double calculateDeliveryFee(String cityName, String vehicleType) {
        validateVehicleType(vehicleType);
        WeatherCondition weatherCondition = getWeatherCondition(cityName);
        return calculateRBF(cityName, vehicleType) + calculateWeatherFees(weatherCondition, vehicleType);
    }

    private void validateVehicleType(String vehicleType) {
        boolean contains = false;

        for (String type : Constants.VEHICLE_TYPES) {
            if (type.equalsIgnoreCase(vehicleType)) {
                contains = true;
                break;
            }
        }

        if (!contains) {
            throw new AppException("Invalid vehicle type - " + vehicleType, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private WeatherCondition getWeatherCondition(String cityName) {
        return weatherConditionService.getLatestWeatherConditionByCity(cityName).orElseThrow(
                () -> new AppException("No weather data available in Tallinn " + cityName, HttpStatus.NOT_FOUND));
    }

    public Double calculateRBF(String cityName, String vehicleType) {
        Double fee = Constants.MAX_RBF_FEE;

        fee -= calculateFee(cityName, Constants.CITY_NAMES);
        fee -= calculateFee(vehicleType, Constants.VEHICLE_TYPES);

        return fee;
    }

    private Double calculateFee(String value, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (value.equalsIgnoreCase(list.get(i))) {
                return 0.5d * i;
            }
        }
        return 0d;
    }

    public Double calculateWeatherFees(WeatherCondition weatherCondition, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Car")) return 0d;

        Double fee = calculateATEF(weatherCondition.getAirTemperature(), vehicleType);
        fee += calculateWSEF(weatherCondition.getWindSpeed(), vehicleType);
        fee += calculateWPEF(weatherCondition.getWeatherPhenomenon(), vehicleType);

        return fee;
    }

    @Override
    public Double calculateATEF(Double airTemperature, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("car")) {
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
        if (windSpeed > 20 && vehicleType.equalsIgnoreCase("bike")) {
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
        if (vehicleType.equalsIgnoreCase("car")) {
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
