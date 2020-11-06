package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Order;
import com.davidhorstman.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Order save(Order order) {
        return ordersRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        ordersRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Order findById(long orderid) {
        return ordersRepository.findById(orderid).orElseThrow(() ->
                new EntityNotFoundException("Order with id " + orderid + " not found."));
    }

}
