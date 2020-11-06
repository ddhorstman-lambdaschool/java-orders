package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Customer;

import java.util.List;

public interface CustomerServices {
    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(long custcode);
}
