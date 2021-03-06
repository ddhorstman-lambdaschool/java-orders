package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Order;
import com.davidhorstman.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Order save(Order order) {
        return ordersRepository.save(order);
    }
}
