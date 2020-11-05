package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentsRepository extends CrudRepository<Agent, Long> {
}
