package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getCustomerByName(@PathVariable String name){
        return new ResponseEntity<>(customerServices.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/byordercount")
    public ResponseEntity<?> getCustomersByOrderCount(){
        return new ResponseEntity<>(customerServices.findAllByOrderCount(), HttpStatus.OK);
    }

    @DeleteMapping("/{custcode}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long custcode){
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
