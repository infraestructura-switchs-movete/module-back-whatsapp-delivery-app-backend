package com.restaurante.delivery.business.service;

import com.restaurante.delivery.business.interfaces.IOrderDetailBusiness;
import com.restaurante.delivery.dto.SellProducts;
import com.restaurante.delivery.dto.request.OrderDetailsDTO;
import com.restaurante.delivery.dto.request.OrderStatusDTO;
import com.restaurante.delivery.dto.responde.OrderProductResponseDTO;
import com.restaurante.delivery.dto.responde.OrderResponseDTO;
import com.restaurante.delivery.model.*;
import com.restaurante.delivery.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class OrderDetailsService implements IOrderDetailBusiness {

    private final OrderDetailRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    public OrderDetail saveOrder(OrderDetailsDTO orderDetailsDTO) {

        Optional<Customer> existingCustomerOptional = customerRepository.findByPhone(orderDetailsDTO.getPhone());

        Customer customer;

        if (existingCustomerOptional.isPresent()) {
            customer = existingCustomerOptional.get();
            customer.setName(orderDetailsDTO.getNameClient());
            customer.setEmail(orderDetailsDTO.getMail());
            customer.setAddress(orderDetailsDTO.getAddress());
            customer.setNumerIdentification(orderDetailsDTO.getNameIdentification());
            customer.setTypeIdentificationId(orderDetailsDTO.getTypeIdentificationId());

            customer = customerRepository.save(customer);
        } else {
            customer = Customer.builder()
                    .name(orderDetailsDTO.getNameClient())
                    .phone(orderDetailsDTO.getPhone())
                    .email(orderDetailsDTO.getMail())
                    .address(orderDetailsDTO.getAddress())
                    .numerIdentification(orderDetailsDTO.getNameIdentification())
                    .typeIdentificationId(orderDetailsDTO.getTypeIdentificationId())
                    .build();

            customer = customerRepository.save(customer);
        }

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setTotal(orderDetailsDTO.getTotal());
        orderDetail.setStatusOrder("PENDIENTE");
        orderDetail.setMethod(orderDetailsDTO.getMethod());
        orderDetail.setStatus("ACTIVE");
        orderDetail.setPaymentId(orderDetailsDTO.getPaymentId());
        orderDetail.setCustomerId(customer.getId());

        orderDetail = orderRepository.save(orderDetail);

        for (SellProducts productDTO : orderDetailsDTO.getProducts()) {
            if (productDTO.getProductId() == null || productDTO.getProductId() <= 0) {
                throw new IllegalArgumentException("Invalid product ID");
            }

            Product product = productRepository.findById(productDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderProductDelivery orderProduct = new OrderProductDelivery();
            orderProduct.setOrderTransactionDeliveryId(orderDetail.getOrderTransactionDeliveryId());
            orderProduct.setProductId(product.getProductId());
            orderProduct.setQuantity(productDTO.getQuantity());
            orderProduct.setUnitPrice(product.getPrice());

            orderProductRepository.save(orderProduct);
        }

        return orderDetail;
    }


    @Override
    public List<OrderResponseDTO> getOrderDetails() {
        List<OrderResponseDTO> orderResponses = orderRepository.getOrderDetail();

        List<Long> orderIds = orderResponses.stream()
                .map(OrderResponseDTO::getOrderTransactionDeliveryId)
                .collect(Collectors.toList());

        List<OrderProductResponseDTO> allProducts = orderRepository.getOrderDetailProduct(orderIds);

        Map<Long, List<SellProducts>> productsGroupedByOrder = allProducts.stream()
                .collect(Collectors.groupingBy(
                        OrderProductResponseDTO::getOrderTransactionDeliveryId,
                        Collectors.mapping(op -> {
                            SellProducts product = new SellProducts();
                            product.setProductId(op.getProductId());
                            product.setProductName(op.getName());
                            product.setQuantity(op.getQuantity());
                            product.setPrice(op.getPrice());
                            return product;
                        }, Collectors.toList())
                ));

        for (OrderResponseDTO dto : orderResponses) {
            dto.setProducts(productsGroupedByOrder.getOrDefault(dto.getOrderTransactionDeliveryId(), List.of()));
        }

        return orderResponses;
    }


    public OrderDetail updateOrderStatus(Long orderTransactionDeliveryId, OrderStatusDTO updateOrderStatusDTO) {
        OrderDetail orderDetail = orderRepository.findById(orderTransactionDeliveryId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderDetail.setStatusOrder(updateOrderStatusDTO.getOrderStatus());

        return orderRepository.save(orderDetail);
    }


    @Override
    public Boolean delete(Long id) {
        if (orderRepository.existsById(id)) {
            OrderDetail orderDetail = orderRepository.findById(id).get();
            orderDetail.setStatus("INACTIVE");
            orderRepository.save(orderDetail);
            return true;
        } else {
            throw new RuntimeException("El pedido no fue encontrada por el id " + id);
        }
    }






}



