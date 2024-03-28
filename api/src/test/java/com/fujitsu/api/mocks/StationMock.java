package com.fujitsu.api.mocks;

import com.fujitsu.api.entities.Station;


public class StationMock {
    public static Station getStationMock(Integer wmoCode) {
        return Station.builder()
                .wmoCode(wmoCode)
                .name("Tallinn-Harku")
                .city("Tallinn")
                .build();
    }
}
