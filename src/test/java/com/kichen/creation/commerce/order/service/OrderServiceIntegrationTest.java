package com.kichen.creation.commerce.order.service;

import com.kichen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kichen.creation.commerce.order.repository.OrderRepository;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class OrderServiceIntegrationTest {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void createOrderMultiThreadAccess() throws InterruptedException {
        int poolSize = 10000;
        Product product = new Product(
                "test product",
                30.0f,
                10
        );
        Product savedProduct = productRepository.save(product);

        List<OrderLineRequestDto> orderLineRequestDtoList = new ArrayList<>();
        OrderLineRequestDto orderLineRequestDto = new OrderLineRequestDto(
                savedProduct.getId(),
                10
        );
        orderLineRequestDtoList.add(orderLineRequestDto);

        CountDownLatch latch = new CountDownLatch(poolSize);
        CountDownLatch latch2 = new CountDownLatch(poolSize);

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i=0; i<poolSize; i++) {
            executorService.submit(() -> {
                try {
                    latch.await();
                    orderService.createOrder(orderLineRequestDtoList);

                } catch (Exception e) {}

                latch2.countDown();
            });

            latch.countDown();
        }

        latch2.await();
        Assertions.assertThat(orderRepository.findAll().size()).isEqualTo(1);
    }
}
