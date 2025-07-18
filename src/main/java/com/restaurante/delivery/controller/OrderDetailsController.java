package com.restaurante.delivery.controller;

import com.restaurante.delivery.business.service.OrderDetailsService;
import com.restaurante.delivery.dto.request.OrderDetailsDTO;
import com.restaurante.delivery.dto.request.OrderStatusDTO;
import com.restaurante.delivery.dto.responde.OrderResponseDTO;
import com.restaurante.delivery.model.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${app.request.prefix}/${app.request.version}${app.request.mappings}/order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class OrderDetailsController {
    private final OrderDetailsService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDetail> createOrder(@RequestBody OrderDetailsDTO orderDetailsDTO) {
        log.info("Creando una nueva orden");
        try {
            OrderDetail createdOrder = orderService.saveOrder(orderDetailsDTO);
            return ResponseEntity.status(201).body(createdOrder);
        } catch (Exception e) {
            log.error("Error al crear la orden: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        log.info("Obteniendo todas las órdenes activas");
        try {
            List<OrderResponseDTO> activeOrders = orderService.getOrderDetails();
            return ResponseEntity.ok(activeOrders);
        } catch (Exception e) {
            log.error("Error al obtener las órdenes activas: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/updateStatus/{orderTransactionDeliveryId}")
    public ResponseEntity<OrderDetail> updateOrderStatus(@PathVariable Long orderTransactionDeliveryId, @RequestBody OrderStatusDTO updateOrderStatusDTO) {

        log.info("Actualizando el estado de la orden con ID: {}", orderTransactionDeliveryId);
        try {
            OrderDetail updatedOrder = orderService.updateOrderStatus(orderTransactionDeliveryId, updateOrderStatusDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            log.error("Error al actualizar el estado de la orden: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }






}
