package com.eldorado.service.parsers;

import com.eldorado.model.Customer;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panasenko on 16.03.2015.
 */
public class RootElementParser implements ElementParser {

    List<Customer> parsedCustomers = new ArrayList<Customer>();

    private static final QName customerTagName = new QName("customer");

    private CustomerElementParser customerElementParser = new CustomerElementParser(this);


    @Override
    public ElementParser processStartElement(StartElement element) {
        if (customerTagName.equals(element.getName())) {
            Customer currentCustomer = new Customer();
            customerElementParser.setCurrentCustomer(currentCustomer);
            return customerElementParser;
        }
        return this;
    }

    @Override
    public ElementParser processEndElement(EndElement element) {
        if (customerTagName.equals(element.getName())) {
            parsedCustomers.add(customerElementParser.getCurrentCustomer());
            return this;
        }
        return this;
    }

    @Override
    public void processCharacters(Characters characters) {

    }

    public void init() {
        parsedCustomers = new ArrayList<Customer>();
    }

    public List<Customer> getParsedCustomers() {
        return parsedCustomers;
    }

}
