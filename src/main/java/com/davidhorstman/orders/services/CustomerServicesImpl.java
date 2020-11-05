package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Customer;
import com.davidhorstman.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    CustomersRepository customersRepository;

    @Override
    public Customer save(Customer customer) {
        return customersRepository.save(customer);
    }
}
