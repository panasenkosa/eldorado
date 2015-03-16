package com.eldorado.service.parsers;

import com.eldorado.model.Customer;
import com.eldorado.model.Order;
import com.eldorado.service.parsers.ElementParser;

import javax.lang.model.element.Element;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.util.LongSummaryStatistics;

/**
 * Created by panasenko on 16.03.2015.
 */
public class CustomerElementParser implements ElementParser {

    private RootElementParser  parentParser;
    private OrderElementParser orderElementParser = new OrderElementParser(this);

    private Customer currentCustomer;

    private static final QName orderTagName = new QName("order");
    private static final QName idTagName = new QName("id");
    private static final QName nameTagName = new QName("name");
    private static final QName customerTagName = new QName("customer");

    private ElementContext currentContext;

    public CustomerElementParser(RootElementParser parentParser) {
        this.parentParser = parentParser;
    }

    @Override
    public ElementParser processStartElement(StartElement element) {
        if (idTagName.equals(element.getName())) {
            currentContext = ElementContext.ID;
        }
        if (nameTagName.equals(element.getName())) {
            currentContext = ElementContext.NAME;
        }
        if (orderTagName.equals(element.getName())) {
            Order currentOrder = new Order();
            orderElementParser.setCurrentOrder(currentOrder);
            return orderElementParser;
        }
        return this;
    }

    @Override
    public ElementParser processEndElement(EndElement element) {
        currentContext = null;
        if (orderTagName.equals(element.getName())) {
            getCurrentCustomer().getOrders().add(orderElementParser.getCurrentOrder());
            return this;
        }
        if (customerTagName.equals(element.getName())) {
            return parentParser.processEndElement(element);
        }
        return this;
    }

    @Override
    public void processCharacters(Characters characters) {
        if (currentContext==null) return;
        switch (currentContext) {
            case ID:
                currentCustomer.setId(Long.valueOf(characters.getData()));
                break;
            case NAME:
                currentCustomer.setName(characters.getData());
                break;
            default:
                break;

        }
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static enum ElementContext {
        ID, NAME
    }
}
