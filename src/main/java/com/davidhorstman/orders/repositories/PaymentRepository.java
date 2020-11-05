package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
