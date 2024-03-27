package com.fujitsu.api.mocks;

import com.fujitsu.api.entities.RegionalBaseFee;

public class RegionalBaseFeeMock {
    public static RegionalBaseFee getRegionalBaseFeeMock(Long id) {
        return RegionalBaseFee.builder()
                .id(id)
                .city("Tallinn")
                .vehicleType("Car")
                .fee(4d)
                .build();
    }
}
