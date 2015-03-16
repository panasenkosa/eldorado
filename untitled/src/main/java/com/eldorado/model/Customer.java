package com.eldorado.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panasenko on 16.03.2015.
 */
public class Customer {

    private Long    id;
    private String  name;
    private List<Order> orders = new ArrayList<Order>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Float getTotalOrdersSum() {
        Float total = 0.0f;
        for (Order order : getOrders()) {
            total += order.getTotalSum();
        }
        return total;
    }

    public Float getMaxOrdersSum() {
        Float maxSum = 0.0f;
        for (Order order : getOrders()) {
            Float currentSum = order.getTotalSum();
            if (currentSum>maxSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }

    public Float getMinOrdersSum() {
        Float minSum = 0.0f;
        for (Order order : getOrders()) {
            Float currentSum = order.getTotalSum();
            if (currentSum<minSum || minSum.equals(0.0f)) {
                minSum = currentSum;
            }
        }
        return minSum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
