package com.fujitsu.api.services;

import com.fujitsu.api.exceptions.AppException;

public interface WeatherService {
    void updateWeatherData() throws AppException;
}
