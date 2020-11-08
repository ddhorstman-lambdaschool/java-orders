package com.davidhorstman.orders.controllers;

import com.davidhorstman.orders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    AgentServices agentServices;

    @GetMapping(value = "all", produces = "application/json")
    public ResponseEntity<?> getAllAgents() {
        return new ResponseEntity<>(agentServices.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{agentcode}", produces = "application/json")
    public ResponseEntity<?> getAgentById(@PathVariable long agentcode) {
        return new ResponseEntity<>(agentServices.findById(agentcode), HttpStatus.OK);
    }

    @DeleteMapping("/unassigned/{agentcode}")
    public ResponseEntity<?> deleteUnassignedAgentById(@PathVariable long agentcode) {
        agentServices.deleteUnassigned(agentcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}