package com.fujitsu.api.repositories;

import com.fujitsu.api.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findByName(String name);
    Optional<Station> findByCity(String city);
    Optional<Station> findByWmoCode(Integer wmoCode);

    @Query("SELECT s.name FROM Station s")
    List<String> findAllNames();
}
