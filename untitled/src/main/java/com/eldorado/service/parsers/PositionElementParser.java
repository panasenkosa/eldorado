package com.eldorado.service.parsers;

import com.eldorado.model.OrderPosition;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

/**
 * Created by panasenko on 16.03.2015.
 */
public class PositionElementParser implements ElementParser {

    private OrderElementParser    parentParser;

    private OrderPosition currentPosition;

    private static final QName positionTagName = new QName("position");

    private static final QName priceTagName = new QName("price");
    private static final QName countTagName = new QName("count");
    private static final QName idTagName = new QName("id");

    private ElementContext currentContext;

    public PositionElementParser(OrderElementParser parentParser) {
        this.parentParser = parentParser;
    }

    @Override
    public ElementParser processStartElement(StartElement element) {
        if (idTagName.equals(element.getName())) {
            currentContext = ElementContext.ID;
        }
        if (priceTagName.equals(element.getName())) {
            currentContext = ElementContext.PRICE;

        }
        if (countTagName.equals(element.getName())) {
            currentContext = ElementContext.COUNT;
        }
        return this;
    }

    @Override
    public ElementParser processEndElement(EndElement element) {
        currentContext = null;
        if (positionTagName.equals(element.getName())) {
            return parentParser.processEndElement(element);
        }
        return this;
    }

    @Override
    public void processCharacters(Characters characters) {
        if (currentContext==null) return;
        switch (currentContext) {
            case ID:
                currentPosition.setId(Long.valueOf(characters.getData()));
                break;
            case PRICE:
                currentPosition.setPrice(Float.valueOf(characters.getData()));
                break;
            case COUNT:
                currentPosition.setCount(Integer.valueOf(characters.getData()));
                break;
            default:
                break;

        }
    }

    public OrderPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(OrderPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public static enum ElementContext {
        ID,PRICE,COUNT
    }
}
