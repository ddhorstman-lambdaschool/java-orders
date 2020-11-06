package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerServices customerServices;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(customerServices.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{custcode}")
    public ResponseEntity<?> getCustomerById(@PathVariable long custcode) {
        return new ResponseEntity<>(customerServices.findById(custcode), HttpStatus.OK);
    }
}
