package com.eldorado.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panasenko on 16.03.2015.
 */
public class Order {

    private Long id;
    private List<OrderPosition> orderPositions = new ArrayList<OrderPosition>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public void setOrderPositions(List<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }

    public Float getTotalSum() {
        Float sum = 0.0f;
        for (OrderPosition position : getOrderPositions()) {
            sum += position.getPrice()*position.getCount();
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
