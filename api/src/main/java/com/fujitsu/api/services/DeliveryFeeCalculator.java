package com.fujitsu.api.services;

public interface DeliveryFeeCalculator {
    Double calculateRBF(String cityName, String vehicleType);
    Double calculateATEF(Double airTemperature, String vehicleType);
    Double calculateWSEF(Double windSpeed, String vehicleType);
    Double calculateWPEF(String phenomenon, String vehicleType);
}
