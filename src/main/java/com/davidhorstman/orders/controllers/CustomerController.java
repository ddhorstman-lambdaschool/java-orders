package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.models.Customer;
import com.davidhorstman.orders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<?> getCustomerByName(@PathVariable String name) {
        return new ResponseEntity<>(customerServices.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/byordercount")
    public ResponseEntity<?> getCustomersByOrderCount() {
        return new ResponseEntity<>(customerServices.findAllByOrderCount(), HttpStatus.OK);
    }

    @DeleteMapping("/{custcode}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long custcode) {
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // A value of "/" would require /customers/, but we want to allow for /customers
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody Customer customer) {
        // This value of 0 indicates that we're saving a new record rather than replacing and old one
        customer.setCustcode(0);
        Customer newCustomer = customerServices.save(customer);

        // Redirect to the newly-created Customer
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{customerid}", consumes = "application/json")
    public ResponseEntity<?> replaceCustomer(
            @Valid @RequestBody Customer customer,
            @PathVariable long customerid) {
        // This value of 0 indicates that we're saving a new record rather than replacing and old one
        customer.setCustcode(customerid);
        Customer newCustomer = customerServices.save(customer);

        // Redirect to the newly-created Customer
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand()
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
    }
}
