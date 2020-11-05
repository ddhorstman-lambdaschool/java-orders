package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long> {
}
