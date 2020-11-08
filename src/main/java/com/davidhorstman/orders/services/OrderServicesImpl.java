package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Order;
import com.davidhorstman.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    @Transactional
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

    @Override
    public List<Order> findWithAdvanceAmount() {
        return ordersRepository.findByAdvanceamountGreaterThan(0.0);
    }

    @Override
    @Transactional
    public void deleteById(long orderid) {
        // Confirm ID exists - if not this method will throw an exception
        findById(orderid);
        ordersRepository.deleteById(orderid);
    }

}
