package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Agent;

import java.util.List;

public interface AgentServices {
    Agent save(Agent agent);

    List<Agent> findAll();

    Agent findById(long agentcode);
}
