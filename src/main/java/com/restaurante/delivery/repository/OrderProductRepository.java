package com.restaurante.delivery.repository;

import com.restaurante.delivery.model.OrderProductDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OrderProductRepository extends JpaRepository<OrderProductDelivery, Long> {

    List<OrderProductDelivery> findByOrderTransactionDeliveryId(Long orderTransactionDeliveryId);


}
