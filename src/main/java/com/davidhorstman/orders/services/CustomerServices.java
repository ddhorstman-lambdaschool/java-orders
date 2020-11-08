package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Customer;
import com.davidhorstman.orders.views.CustomerOrderCount;

import java.util.List;

public interface CustomerServices {
    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(long custcode);

    List<Customer> findByName(String name);

    List<CustomerOrderCount> findAllByOrderCount();

    void delete(long custcode);
}
