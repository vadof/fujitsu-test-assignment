package com.fujitsu.api.services;

import com.fujitsu.api.config.Constants;
import com.fujitsu.api.dtos.xml.Observations;
import com.fujitsu.api.dtos.xml.Station;
import com.fujitsu.api.entities.WeatherCondition;
import com.fujitsu.api.utils.ObservationsParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.data.url}")
    private String weatherDataUrl;

    private final RestTemplate restTemplate;
    private final WeatherConditionService weatherConditionService;

    @Override
    public void updateWeatherData() {
        try {
            String xmlString = restTemplate.getForObject(weatherDataUrl, String.class);
            Observations observations = new ObservationsParser().parseXML(xmlString);

            observations.setStations(filterStationsByName(observations.getStations(), Constants.STATION_NAMES));
            saveObservations(observations);

            log.info("Weather Observations have been updated");
        } catch (JAXBException e) {
            log.error("Failed to update Weather Observations {}", e.getMessage());
        }
    }

    private List<Station> filterStationsByName(List<Station> stations, Set<String> stationNames) {
        return stations.stream()
                .filter(s -> stationNames.contains(s.getName()))
                .toList();
    }

    public void saveObservations(Observations observations) {
        Timestamp timestamp = getUnixTimestamp(observations.getTimestamp());

        for (Station stationXML : observations.getStations()) {
            WeatherCondition weatherCondition = stationToWeatherCondition(stationXML, timestamp);
            weatherConditionService.save(weatherCondition);
        }
    }

    public WeatherCondition stationToWeatherCondition(Station station, Timestamp timestamp) {
        String phenomenon = station.getPhenomenon().length() == 0 ?
                Constants.DEFAULT_PHENOMENON : station.getPhenomenon();

        return WeatherCondition.builder()
                .wmoCode(station.getWmocode())
                .stationName(station.getName())
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
