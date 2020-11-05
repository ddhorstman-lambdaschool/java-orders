package com.davidhorstman.orders.repositories;

import com.davidhorstman.orders.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentsRepository extends CrudRepository<Payment, Long> {
}
