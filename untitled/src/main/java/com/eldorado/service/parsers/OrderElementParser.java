package com.eldorado.service.parsers;

import com.eldorado.model.Order;
import com.eldorado.model.OrderPosition;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

/**
 * Created by panasenko on 16.03.2015.
 */
public class OrderElementParser implements ElementParser {

    private CustomerElementParser    parentParser;
    private PositionElementParser    positionElementParser = new PositionElementParser(this);

    private Order currentOrder;

    private static final QName positionTagName = new QName("position");
    private static final QName orderTagName = new QName("order");
    private static final QName idTagName = new QName("id");

    private ElementContext currentContext;

    public OrderElementParser(CustomerElementParser parentParser) {
        this.parentParser = parentParser;
    }

    @Override
    public ElementParser processStartElement(StartElement element) {
        if (idTagName.equals(element.getName())) {
            currentContext = ElementContext.ID;
        }
        if (positionTagName.equals(element.getName())) {
            OrderPosition position = new OrderPosition();
            positionElementParser.setCurrentPosition(position);
            return positionElementParser;
        }
        return this;
    }

    @Override
    public ElementParser processEndElement(EndElement element) {
        currentContext = null;
        if (positionTagName.equals(element.getName())) {
            getCurrentOrder().getOrderPositions().add(positionElementParser.getCurrentPosition());
            return this;
        }
        if (orderTagName.equals(element.getName())) {
            return parentParser.processEndElement(element);
        }
        return this;
    }

    @Override
    public void processCharacters(Characters characters) {
        if (currentContext==null) return;
        switch (currentContext) {
            case ID:
                currentOrder.setId(Long.valueOf(characters.getData()));
                break;
            default:
                break;

        }
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public static enum ElementContext {
        ID
    }
}
