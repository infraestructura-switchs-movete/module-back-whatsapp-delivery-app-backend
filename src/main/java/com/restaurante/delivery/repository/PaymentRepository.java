package com.restaurante.delivery.repository;


import com.restaurante.delivery.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
