package com.fujitsu.api.services.weather;

import com.fujitsu.api.exceptions.AppException;

public interface WeatherService {
    void updateWeatherData() throws AppException;
}
