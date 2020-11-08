package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.models.Order;
import com.davidhorstman.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    @GetMapping(value = "/advanceamount", produces = "application/json")
    public ResponseEntity<?> getOrdersWithAdvanceAmount() {
        return new ResponseEntity<>(orderServices.findWithAdvanceAmount(), HttpStatus.OK);
    }

    @DeleteMapping("/{orderid}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long orderid) {
        orderServices.deleteById(orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // A value of "/" would require /orders/, but we want to allow for /orders
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> createNewOrder(@Valid @RequestBody Order order) {
        // This value of 0 indicates that we're saving a new record rather than replacing and old one
        order.setOrdnum(0);
        Order newOrder = orderServices.save(order);

        // Redirect to the newly-created Order
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value="/{orderid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceOrder(@PathVariable long orderid, @Valid @RequestBody Order order){
        // Add the existing order ID to the new record which will replace it
        order.setOrdnum(orderid);
        Order newOrder = orderServices.save(order);

        // Redirect to the replaced Order
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand()
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
    }
}
