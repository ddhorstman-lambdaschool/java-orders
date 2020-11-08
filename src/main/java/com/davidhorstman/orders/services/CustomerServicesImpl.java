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
                        + " to customer: agent not found.")
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
                () -> new EntityNotFoundException("Could not update customer with id " + customerid + ": customer not found.")
        );

        if (updatedCustomer.getCustcity() != null)
            currentCustomer.setCustcity(updatedCustomer.getCustcity());
        if (updatedCustomer.getCustname() != null)
            currentCustomer.setCustname(updatedCustomer.getCustname());
        if (updatedCustomer.getCustcountry() != null)
            currentCustomer.setCustcountry(updatedCustomer.getCustcountry());
        if (updatedCustomer.getWorkingarea() != null)
            currentCustomer.setWorkingarea(updatedCustomer.getWorkingarea());
        if (updatedCustomer.getGrade() != null)
            currentCustomer.setGrade(updatedCustomer.getGrade());
        if (updatedCustomer.getPhone() != null)
            currentCustomer.setPhone(updatedCustomer.getPhone());
        if (updatedCustomer.hasOpeningamt)
            currentCustomer.setOpeningamt(updatedCustomer.getOpeningamt());
        if (updatedCustomer.hasOutstandingamt)
            currentCustomer.setOutstandingamt(updatedCustomer.getOutstandingamt());
        if (updatedCustomer.hasReceiveamt)
            currentCustomer.setReceiveamt(updatedCustomer.getReceiveamt());
        if (updatedCustomer.hasPaymentamt)
            currentCustomer.setPaymentamt(updatedCustomer.getPaymentamt());

        // Update agent if applicable
        Agent a = updatedCustomer.getAgent();
        if (a != null) {
            Long agentcode = a.getAgentcode();
            currentCustomer.setAgent(agentsRepository.findById(agentcode).orElseThrow(
                    () -> new EntityNotFoundException("Error changing to agent with id "
                            + agentcode
                            + " for customer: agent not found.")
            ));
        }

        // Update order list if applicable
        if (updatedCustomer.hasOrders) {
            currentCustomer.getOrders().clear();
            for (Order o : updatedCustomer.getOrders()) {
                o.setCustomer(currentCustomer);
                orderServices.save(o);
            }
        }

        return customersRepository.save(currentCustomer);
    }
}
