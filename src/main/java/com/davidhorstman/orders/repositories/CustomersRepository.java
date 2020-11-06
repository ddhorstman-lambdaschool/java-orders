package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAllByCustnameContainsIgnoreCase(String name);
}
