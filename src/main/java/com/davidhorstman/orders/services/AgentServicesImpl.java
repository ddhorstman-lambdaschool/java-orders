package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Agent;
import com.davidhorstman.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "agentServices")
public class AgentServicesImpl implements  AgentServices {
    @Autowired
    AgentsRepository agentsRepository;

    @Override
    public Agent save(Agent agent) {
        return agentsRepository.save(agent);
    }
}
