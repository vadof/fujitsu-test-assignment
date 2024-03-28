package com.fujitsu.api.services.weather;

import com.fujitsu.api.config.Constants;
import com.fujitsu.api.dtos.xml.ObservationsXML;
import com.fujitsu.api.dtos.xml.StationXML;
import com.fujitsu.api.entities.Station;
import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.exceptions.AppException;
import com.fujitsu.api.services.StationService;
import com.fujitsu.api.utils.ObservationsParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.data.url}")
    private String weatherDataUrl;

    private final RestTemplate restTemplate;

    private final WeatherConditionService weatherConditionService;
    private final StationService stationService;

    @Override
    public void updateWeatherData() {
        try {
            String xmlString = restTemplate.getForObject(weatherDataUrl, String.class);
            ObservationsXML observations = new ObservationsParser().parseXML(xmlString);

            List<String> stationNames = stationService.getAllStationNames();
            observations.setStations(filterStationsByName(observations.getStations(), stationNames));
            saveObservations(observations);

            log.debug("Weather Observations have been updated");
        } catch (JAXBException e) {
            log.error("Failed to update Weather Observations {}", e.getMessage());
        }
    }

    private List<StationXML> filterStationsByName(List<StationXML> stations, List<String> stationNames) {
        return stations.stream()
                .filter(s -> stationNames.contains(s.getName()))
                .toList();
    }

    public void saveObservations(ObservationsXML observations) {
        Timestamp timestamp = getUnixTimestamp(observations.getTimestamp());

        for (StationXML stationXML : observations.getStations()) {
            WeatherCondition weatherCondition = stationToWeatherCondition(stationXML, timestamp);
            weatherConditionService.save(weatherCondition);
        }
    }

    public WeatherCondition stationToWeatherCondition(StationXML station, Timestamp timestamp) {
        String phenomenon = Strings.isBlank(station.getPhenomenon()) ?
                Constants.DEFAULT_WEATHER_PHENOMENON : station.getPhenomenon();

        Station existingStation = stationService.getByWmoCode(station.getWmocode())
                .orElseThrow(() -> new AppException("Station#" + station.getWmocode() + " not found", HttpStatus.NOT_FOUND));

        return WeatherCondition.builder()
                .station(existingStation)
                .airTemperature(station.getAirtemperature())
                .windSpeed(station.getWindspeed())
                .weatherPhenomenon(phenomenon)
                .timestamp(timestamp)
                .build();
    }

    private Timestamp getUnixTimestamp(Long value) {
        return new Timestamp(value * 1000);
    }
}
