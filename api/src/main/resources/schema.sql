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

CREATE TABLE regional_base_fee
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    city         VARCHAR(255)  NOT NULL,
    vehicle_type VARCHAR(255)  NOT NULL,
    fee          DECIMAL(3, 1) NOT NULL,
    UNIQUE (city, vehicle_type)
);

INSERT INTO regional_base_fee (city, vehicle_type, fee)
VALUES ('Tallinn', 'Car', 4.0),
       ('Tallinn', 'Scooter', 3.5),
       ('Tallinn', 'Bike', 3.0),
       ('Tartu', 'Car', 3.5),
       ('Tartu', 'Scooter', 3.0),
       ('Tartu', 'Bike', 2.5),
       ('Pärnu', 'Car', 3.0),
       ('Pärnu', 'Scooter', 2.5),
       ('Pärnu', 'Bike', 2.0);