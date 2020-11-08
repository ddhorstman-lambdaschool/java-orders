package com.davidhorstman.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "customers")
public class Customer {
    @Transient
    @JsonIgnore
    public boolean hasOpeningamt = false;

    @Transient
    @JsonIgnore
    public boolean hasOutstandingamt = false;

    @Transient
    @JsonIgnore
    public boolean hasReceiveamt = false;

    @Transient
    @JsonIgnore
    public boolean hasPaymentamt = false;

    @Transient
    @JsonIgnore
    public boolean hasOrders = false;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long custcode;

    @Column(nullable = false)
    private String custname;
    private String custcity, workingarea, custcountry, grade, phone;
    private double openingamt, receiveamt, paymentamt, outstandingamt;

    @ManyToOne
    @JoinColumn(name = "agentcode",
            nullable = false)
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Agent agent;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "customer", allowSetters = true)
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String custname,
                    String custcity,
                    String workingarea,
                    String custcountry,
                    String grade,
                    double openingamt,
                    double receiveamt,
                    double paymentamt,
                    double outstandingamt,
                    String phone,
                    Agent agent) {
        this.custname = custname;
        this.custcity = custcity;
        this.workingarea = workingarea;
        this.custcountry = custcountry;
        this.grade = grade;
        this.phone = phone;
        this.openingamt = openingamt;
        this.hasOpeningamt = true;
        this.receiveamt = receiveamt;
        this.hasReceiveamt = true;
        this.paymentamt = paymentamt;
        this.hasPaymentamt = true;
        this.outstandingamt = outstandingamt;
        this.hasOutstandingamt = true;
        this.agent = agent;
    }

    public long getCustcode() {
        return custcode;
    }

    public void setCustcode(long custcode) {
        this.custcode = custcode;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public String getCustcountry() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry = custcountry;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getOpeningamt() {
        return openingamt;
    }

    public void setOpeningamt(double openingamt) {
        this.hasOpeningamt = true;
        this.openingamt = openingamt;
    }

    public double getReceiveamt() {
        return receiveamt;
    }

    public void setReceiveamt(double receiveamt) {
        this.hasReceiveamt = true;
        this.receiveamt = receiveamt;
    }

    public double getPaymentamt() {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt) {
        this.hasPaymentamt = true;
        this.paymentamt = paymentamt;
    }

    public double getOutstandingamt() {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt) {
        this.hasOutstandingamt = true;
        this.outstandingamt = outstandingamt;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.hasOrders = true;
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custcode=" + custcode +
                ", custname='" + custname + '\'' +
                ", custcity='" + custcity + '\'' +
                ", workingarea='" + workingarea + '\'' +
                ", custcountry='" + custcountry + '\'' +
                ", grade='" + grade + '\'' +
                ", phone='" + phone + '\'' +
                ", openingamt=" + openingamt +
                ", receiveamt=" + receiveamt +
                ", paymentamt=" + paymentamt +
                ", outstandingamt=" + outstandingamt +
                ", agent_id=" + agent.getAgentcode() +
                ", orders=" + orders.stream().map(o -> "id=" + o.getOrdnum()).collect(Collectors.toList())+
                '}';
    }
}
