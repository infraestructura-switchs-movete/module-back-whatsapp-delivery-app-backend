package com.restaurante.delivery.repository;


import com.restaurante.delivery.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Optional<Customer> findByPhone(String phone);

}
