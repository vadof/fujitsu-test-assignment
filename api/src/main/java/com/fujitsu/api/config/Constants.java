package com.fujitsu.api.config;

import java.util.List;

public class Constants {
    public static List<String> STATION_NAMES = List.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
    public static List<String> CITY_NAMES = List.of("Tallinn", "Tartu", "Pärnu");
    public static List<String> VEHICLE_TYPES = List.of("Car", "Scooter", "Bike");
    public static String DEFAULT_PHENOMENON = "Overcast";
    public static Double MAX_RBF_FEE = 4d;
}
