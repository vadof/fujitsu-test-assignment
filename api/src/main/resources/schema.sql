CREATE TABLE weather_condition
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    station_name       varchar(255)  NOT NULL,
    wmo_code           INT           NOT NULL,
    air_temperature    DECIMAL(3, 1) NOT NULL,
    wind_speed         DECIMAL(4, 1) NOT NULL,
    weather_phenomenon VARCHAR(255)  NOT NULL,
    timestamp          TIMESTAMP     NOT NULL
);