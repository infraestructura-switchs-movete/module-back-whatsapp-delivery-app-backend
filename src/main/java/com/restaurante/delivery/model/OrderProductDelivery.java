package com.restaurante.delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_product_delivery")
public class OrderProductDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_delivery_id")
    private Long orderProductId;

    @Column(name = "order_transaction_delivery_id", nullable = false)
    private Long orderTransactionDeliveryId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;



}
