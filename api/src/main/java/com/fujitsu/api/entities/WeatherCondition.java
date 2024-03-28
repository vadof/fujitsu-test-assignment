package com.fujitsu.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "weather_condition")
public class WeatherCondition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_code", nullable = false)
    private Station station;

    @Column(name = "air_temperature", columnDefinition = "DECIMAL(3,1)", nullable = false)
    private Double airTemperature;

    @Column(name = "wind_speed", columnDefinition = "DECIMAL(4,1)", nullable = false)
    private Double windSpeed;

    @Column(name = "weather_phenomenon", nullable = false)
    private String weatherPhenomenon;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;
}
