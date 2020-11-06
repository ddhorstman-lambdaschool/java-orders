package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Agent;
import com.davidhorstman.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "agentServices")
public class AgentServicesImpl implements  AgentServices {
    @Autowired
    AgentsRepository agentsRepository;

    @Override
    public Agent save(Agent agent) {
        return agentsRepository.save(agent);
    }

    @Override
    public List<Agent> findAll() {
        List<Agent> list = new ArrayList<>();
        agentsRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Agent findById(long agentcode) {
        return agentsRepository.findById(agentcode).orElseThrow(()->
                new EntityNotFoundException("Agent with id "+agentcode+" not found."));
    }
}
