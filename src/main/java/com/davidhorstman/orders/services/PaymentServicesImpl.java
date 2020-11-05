package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Payment;
import com.davidhorstman.orders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "paymentServices")
public class PaymentServicesImpl implements PaymentServices{
    @Autowired
    PaymentsRepository paymentsRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentsRepository.save(payment);
    }
}
