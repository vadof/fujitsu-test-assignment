package com.fujitsu.api.dtos.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "station")
public class StationXML {
    private String name;
    private Integer wmocode;
    private Double airtemperature;
    private Double windspeed;
    private String phenomenon;
}
