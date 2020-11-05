package com.davidhorstman.orders.services;

import com.davidhorstman.orders.models.Payment;

public interface PaymentServices {
    Payment save(Payment payment);
}
