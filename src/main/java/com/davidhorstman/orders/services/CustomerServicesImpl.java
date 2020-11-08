package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Customer;
import com.davidhorstman.orders.repositories.CustomersRepository;
import com.davidhorstman.orders.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    CustomersRepository customersRepository;

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customersRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        customersRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findById(long custcode) {
        return customersRepository.findById(custcode).orElseThrow(() ->
                new EntityNotFoundException("Customer with id " + custcode + " not found."));
    }

    @Override
    public List<Customer> findByName(String name) {
        return customersRepository.findAllByCustnameContainsIgnoreCase(name);
    }

    @Override
    public List<CustomerOrderCount> findAllByOrderCount() {
        return customersRepository.findAllByOrderCount();
    }

    @Override
    @Transactional
    public void delete(long custcode) {
        // Confirm ID exists - if not this method will throw an exception
        findById(custcode);
        customersRepository.deleteById(custcode);
    }
}
