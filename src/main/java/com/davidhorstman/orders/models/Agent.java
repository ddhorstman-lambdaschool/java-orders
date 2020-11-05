package com.davidhorstman.orders.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long agentcode;
    private String agentname, country, phone, workingarea;
    private double commission;

    @OneToMany(mappedBy = "agents",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Customer> customers = new ArrayList<>();

    public Agent() {
    }

    public Agent(String agentname, String country, String phone, String workingarea, double commission) {
        this.agentname = agentname;
        this.country = country;
        this.phone = phone;
        this.workingarea = workingarea;
        this.commission = commission;
    }

    public long getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(long agentcode) {
        this.agentcode = agentcode;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}
