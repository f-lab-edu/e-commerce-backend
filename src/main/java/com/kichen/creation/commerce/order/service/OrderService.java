package com.kichen.creation.commerce.order.service;

import com.kichen.creation.commerce.order.domain.Order;
import com.kichen.creation.commerce.order.domain.OrderLine;
import com.kichen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kichen.creation.commerce.order.dto.OrderResponseDto;
import com.kichen.creation.commerce.order.exception.OrderNotFoundException;
import com.kichen.creation.commerce.order.repository.OrderRepository;
import com.kichen.creation.commerce.product.exception.ProductNotFoundException;
import com.kichen.creation.commerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createOrder(@NonNull List<OrderLineRequestDto> orderLineRequestDtoList) {
        Order order = Order.createOrder(
                orderLineRequestDtoList.stream().map(this::convertToOrderLine).toList()
        );
        orderRepository.save(order);
    }

    public OrderResponseDto findOrder(@NonNull Long id) {
        try {
            return orderRepository.getReferenceById(id).toOrderResponseDto();

        } catch (EntityNotFoundException e) {
            throw new OrderNotFoundException(String.format("Order with (id: %s) is not found!", id), e);
        }
    }

    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(Order::toOrderResponseDto).toList();
    }

    private OrderLine convertToOrderLine(OrderLineRequestDto orderLineRequestDto) {
        Long productId = orderLineRequestDto.getProductId();

        try {
            return new OrderLine(
                    productRepository.getReferenceById(productId),
                    orderLineRequestDto.getCount()
            );

        } catch (EntityNotFoundException e) {
            throw new ProductNotFoundException(
                    String.format("Order cannot be created because product with (id: %s) does not exist!", productId),
                    e
            );
        }

    }
}
