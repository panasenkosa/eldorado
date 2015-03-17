package com.eldorado.service;

import com.eldorado.model.Customer;
import com.eldorado.model.Order;
import com.eldorado.model.OrderPosition;
import com.eldorado.service.parsers.ElementParser;
import com.eldorado.service.parsers.RootElementParser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panasenko on 16.03.2015.
 */
@Service("customersOrdersXMLParserService")
@Scope("session")
public class CustomersOrdersXMLParserService {

    private XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    private RootElementParser rootElementParser;
    private ElementParser currentParser;

    public CustomersOrdersXMLParserService() {
        rootElementParser = new RootElementParser();
        currentParser = rootElementParser;
    }

    public void parse(InputStream is) throws XMLStreamException {

        XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(is);
        while(eventReader.hasNext())
        {
            XMLEvent xmlEvent = eventReader.nextEvent();
            switch(xmlEvent.getEventType())
            {
                case XMLStreamConstants.START_DOCUMENT:
                    rootElementParser.init();
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    currentParser = currentParser.processStartElement(xmlEvent.asStartElement());
                    break;
                case XMLStreamConstants.CHARACTERS:
                    currentParser.processCharacters(xmlEvent.asCharacters());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    currentParser = currentParser.processEndElement(xmlEvent.asEndElement());
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    break;
            }
        }
    }

    public List<Customer> getParsedCustomers() {
        return rootElementParser.getParsedCustomers();
    }

}
