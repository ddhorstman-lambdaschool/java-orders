package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderServices orderServices;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderServices.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{orderid}", produces = "application/json")
    public ResponseEntity<?> getOrderById(@PathVariable long orderid) {
        return new ResponseEntity<>(orderServices.findById(orderid), HttpStatus.OK);
    }

    @GetMapping(value="/advanceamount", produces = "application/json")
    public ResponseEntity<?> getOrdersWithAdvanceAmount(){
        return new ResponseEntity<>(orderServices.findWithAdvanceAmount() ,HttpStatus.OK);
    }

}
