package com.restaurante.delivery.business.interfaces;


import com.restaurante.delivery.dto.request.OrderDetailsDTO;
import com.restaurante.delivery.dto.responde.OrderResponseDTO;
import com.restaurante.delivery.model.OrderDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderDetailBusiness {

    @Transactional
    OrderDetail saveOrder(OrderDetailsDTO orderDetailsDTO);

    List<OrderResponseDTO> getOrderDetails();

    Boolean delete(Long id);
}
