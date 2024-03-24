package com.fujitsu.api.utils;

import com.fujitsu.api.dtos.xml.Observations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class ObservationsParser implements XMLParser<Observations> {

    @Override
    public Observations parseXML(String xmlString) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (Observations) unmarshaller.unmarshal(new StringReader(xmlString));
    }
}
