CREATE TABLE station
(
    wmo_code INT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL UNIQUE,
    city     VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE weather_condition
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    station_code       INT,
    air_temperature    DECIMAL(3, 1) NOT NULL,
    wind_speed         DECIMAL(4, 1) NOT NULL,
    weather_phenomenon VARCHAR(255)  NOT NULL,
    timestamp          TIMESTAMP     NOT NULL,
    FOREIGN KEY (station_code) REFERENCES station(wmo_code)
);

CREATE TABLE regional_base_fee
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    city         VARCHAR(255)  NOT NULL,
    vehicle_type VARCHAR(255)  NOT NULL,
    fee          DECIMAL(3, 1) NOT NULL,
    UNIQUE (city, vehicle_type)
);

INSERT INTO station (wmo_code, name, city)
VALUES ('26038', 'Tallinn-Harku', 'Tallinn'),
       ('26242', 'Tartu-Tõravere', 'Tartu'),
       ('41803', 'Pärnu', 'Pärnu');

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