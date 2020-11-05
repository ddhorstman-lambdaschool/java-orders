package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping(value="/{orderid}", produces = "application/json")
    public ResponseEntity<?> getOrderById(@PathVariable long orderid){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
