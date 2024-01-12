package com.kichen.creation.commerce.order.service;

import com.kichen.creation.commerce.order.domain.Order;
import com.kichen.creation.commerce.order.domain.OrderLine;
import com.kichen.creation.commerce.order.dto.OrderLineDto;
import com.kichen.creation.commerce.order.repository.OrderRepository;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createOrder(@NonNull List<OrderLineDto> orderLineDtoList) {
        Order order = new Order(true, LocalDateTime.now());

        for (OrderLineDto orderLineDto: orderLineDtoList) {
            Product product = productRepository.getReferenceById(orderLineDto.getProductId());
            int count = orderLineDto.getCount();
            OrderLine orderLine = new OrderLine(product, count);
            order.addOrderLine(orderLine);
        }

        orderRepository.save(order);
    }

    public Order findOrder(@NonNull Long id) {
        return orderRepository.getReferenceById(id);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll().stream().toList();
    }
}
