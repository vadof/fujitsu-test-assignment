package com.fujitsu.api.utils;

import com.fujitsu.api.dtos.xml.ObservationsXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class ObservationsParser implements XMLParser<ObservationsXML> {

    @Override
    public ObservationsXML parseXML(String xmlString) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ObservationsXML.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (ObservationsXML) unmarshaller.unmarshal(new StringReader(xmlString));
    }
}
