package com.restaurante.delivery.repository;

import com.restaurante.delivery.dto.responde.OrderProductResponseDTO;
import com.restaurante.delivery.dto.responde.OrderResponseDTO;
import com.restaurante.delivery.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByStatus(String status);

    @Query("""
    SELECT new com.restaurante.delivery.dto.responde.OrderResponseDTO(
        c.phone,
        o.orderTransactionDeliveryId,
        NULL,
        o.total,
        p.id,
        p.namePayment,
        ti.id,
        ti.name,
        o.method,
        c.name,
        c.address,
        c.phone,
        c.email,
        c.numerIdentification,
        o.status,
        o.statusOrder
    )
    FROM OrderDetail o
    LEFT JOIN Customer c ON o.customerId = c.id
    LEFT JOIN TypeIdentification ti ON c.typeIdentificationId = ti.id
    LEFT JOIN Payment p ON o.paymentId = p.id 
    WHERE o.status = 'ACTIVE'
""")
    List<OrderResponseDTO> getOrderDetail();


    @Query("""
    SELECT new com.restaurante.delivery.dto.responde.OrderProductResponseDTO(
        opd.orderTransactionDeliveryId,
        opd.productId,
        opd.name,
        opd.quantity,
        opd.unitPrice
    )
    FROM OrderProductDelivery opd
    WHERE opd.orderTransactionDeliveryId IN :orderIds
    """)
    List<OrderProductResponseDTO> getOrderDetailProduct(@Param("orderIds") List<Long> orderIds);
}
