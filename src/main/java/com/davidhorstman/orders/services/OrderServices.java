package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Order;

import java.util.List;

public interface OrderServices {
    Order save(Order order);
    Order findById(long id);

    List<Order> findAll();

    List<Order> findWithAdvanceAmount();

    void deleteById(long orderid);
}
