package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Customer;
import com.davidhorstman.orders.views.CustomerOrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAllByCustnameContainsIgnoreCase(String name);

    @Query(value = "SELECT c.custname AS name, count(ordnum) AS ordercount " +
            "FROM customers c LEFT JOIN orders o " +
            "ON c.custcode = o.custcode " +
            "GROUP BY c.custname " +
            "ORDER BY ordercount DESC", nativeQuery = true)
    List<CustomerOrderCount> findAllByOrderCount();
}
