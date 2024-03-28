package com.fujitsu.api.services;

import com.fujitsu.api.entities.Station;
import com.fujitsu.api.repositories.StationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StationService {
    private final StationRepository stationRepository;

    public Optional<Station> getByName(String stationName) {
        return stationRepository.findByName(stationName);
    }

    public Optional<Station> getByCity(String cityName) {
        return stationRepository.findByCity(cityName);
    }

    public Optional<Station> getByWmoCode(Integer wmoCode) {
        return stationRepository.findByWmoCode(wmoCode);
    }

    public List<String> getAllStationNames() {
        return stationRepository.findAllNames();
    }
}
