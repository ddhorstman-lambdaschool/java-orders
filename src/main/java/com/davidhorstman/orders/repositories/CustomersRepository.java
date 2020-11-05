package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
}
