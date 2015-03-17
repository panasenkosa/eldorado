package com.eldorado.service;

import com.eldorado.model.Customer;
import com.eldorado.model.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panasenko on 16.03.2015.
 */
@Service("customersOrdersCalculationsService")
@Scope("session")
public class CustomersOrdersCalculationsService {

    private List<Customer> customers;

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Float getTotalOrdersSum() {
        Float total = 0.0f;
        for (Customer customer : customers) {
            total += customer.getTotalOrdersSum();
        }
        return total;
    }

    public String getMaxOrderSumCustomerName() {
        String result = "";
        Float maxSum = 0.0f;
        for (Customer customer : customers) {
            Float currentSum = customer.getTotalOrdersSum();
            if (currentSum>maxSum) {
                result = customer.getName();
                maxSum = currentSum;
            }
        }
        return result;
    }

    public Float getMaxOrdersSum() {
        Float maxSum = 0.0f;
        for (Customer customer : customers) {
            Float currentSum = customer.getMaxOrdersSum();
            if (currentSum>maxSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }

    public Float getMinOrdersSum() {
        Float minSum = 0.0f;
        for (Customer customer : customers) {
            Float currentSum = customer.getMinOrdersSum();
            if (currentSum<minSum || minSum.equals(0.0f)) {
                minSum = currentSum;
            }
        }
        return minSum;
    }

    public Integer getTotalOrdersCount() {
        int orderCount = 0;
        for (Customer customer : customers) {
            orderCount += customer.getOrders().size();
        }
        return orderCount;
    }

    public Float getAverageOrderSum() {

        int orderCount = getTotalOrdersCount();
        if (orderCount>0) {
            return getTotalOrdersSum()/orderCount;
        }
        return null;
    }

}
