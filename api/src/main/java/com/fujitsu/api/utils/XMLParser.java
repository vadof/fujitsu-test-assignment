package com.fujitsu.api.utils;

import javax.xml.bind.JAXBException;

public interface XMLParser<T> {
    T parseXML(String xmlString) throws JAXBException;
}
