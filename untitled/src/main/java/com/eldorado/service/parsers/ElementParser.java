package com.eldorado.service.parsers;

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

/**
 * Created by panasenko on 16.03.2015.
 */
public interface ElementParser {

    public ElementParser processStartElement(StartElement element);
    public ElementParser processEndElement(EndElement element);
    public void processCharacters(Characters characters);

}
