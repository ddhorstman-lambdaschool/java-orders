package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Agent;
import com.davidhorstman.orders.models.Customer;
import com.davidhorstman.orders.models.Order;
import com.davidhorstman.orders.repositories.AgentsRepository;
import com.davidhorstman.orders.repositories.CustomersRepository;
import com.davidhorstman.orders.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    AgentsRepository agentsRepository;

    @Autowired
    OrderServices orderServices;

    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        customersRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findById(long custcode) {
        return customersRepository.findById(custcode).orElseThrow(() ->
                new EntityNotFoundException("Customer with id " + custcode + " not found."));
    }

    @Override
    public List<Customer> findByName(String name) {
        return customersRepository.findAllByCustnameContainsIgnoreCase(name);
    }

    @Override
    public List<CustomerOrderCount> findAllByOrderCount() {
        return customersRepository.findAllByOrderCount();
    }

    @Override
    @Transactional
    public void delete(long custcode) {
        // Confirm ID exists - if not this method will throw an exception
        findById(custcode);
        customersRepository.deleteById(custcode);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        // To update an existing customer with a PUT request
        long custcode;
        if ((custcode = customer.getCustcode()) != 0) {
            customersRepository.findById(custcode).orElseThrow(
                    () -> new EntityNotFoundException(
                            "Could not update customer number " +
                                    custcode +
                                    ": customer not found.")
            );
            newCustomer.setCustcode(custcode);
        }

        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());

        Agent a = customer.getAgent();
        if (a == null) {
            throw new EntityNotFoundException("An agent is required to add the customer.");
        }
        Long agentcode = a.getAgentcode();
        newCustomer.setAgent(agentsRepository.findById(agentcode).orElseThrow(
                () -> new EntityNotFoundException("Error adding agent with id "
                        + agentcode
                        + " to order: agent not found.")
        ));

        // We need to create the customer before we can create their orders
        newCustomer = customersRepository.save(newCustomer);

        for (Order o : customer.getOrders()) {
            o.setCustomer(newCustomer);
            orderServices.save(o);
        }

        return newCustomer;
    }

    @Override
    @Transactional
    public Customer update(Customer updatedCustomer, long customerid) {
        Customer currentCustomer = customersRepository.findById(customerid).orElseThrow(
                ()-> new EntityNotFoundException("Could not update customer with id "+customerid+": customer not found.")
        );

        return customersRepository.save(currentCustomer);
    }
}
