package com.kichen.creation.commerce;

import com.kichen.creation.commerce.order.domain.Order;
import com.kichen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kichen.creation.commerce.order.repository.OrderRepository;
import com.kichen.creation.commerce.order.service.OrderService;
import com.kichen.creation.commerce.product.domain.Product;
import com.kichen.creation.commerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class CommerceApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void createOrderMultiThreadAccess() throws InterruptedException {
        int poolSize = 10;
        Product product = new Product(
                "test product",
                32.0f,
                10
        );

        Product savedProduct = productRepository.save(product);

        List<OrderLineRequestDto> orderLineList = new ArrayList<>();
        OrderLineRequestDto orderLine = new OrderLineRequestDto(
                savedProduct.getId(),
                10
        );
        orderLineList.add(orderLine);

        CountDownLatch latch = new CountDownLatch(poolSize);
        CountDownLatch errorLatch = new CountDownLatch(poolSize - 1);

        Collection<CreateOrderCallable> createOrderCallableList = new ArrayList<>();
        for (int i=0; i<poolSize; i++) {
            createOrderCallableList.add(new CreateOrderCallable(
                    orderService,
                    orderLineList,
                    latch,
                    errorLatch
            ));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        executorService.invokeAll(createOrderCallableList);

        latch.await();
        Assertions.assertThat(orderRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(errorLatch.getCount()).isEqualTo(0);
    }

    static class CreateOrderCallable implements Callable<Order> {
        private final CountDownLatch latch;

        private final List<OrderLineRequestDto> orderLineRequestDtos;
        private final OrderService orderService;

        private final CountDownLatch errorLatch;

        public CreateOrderCallable(
                OrderService orderService,
                List<OrderLineRequestDto> orderLines,
                CountDownLatch latch,
                CountDownLatch errorLatch
        ) {
            this.orderService = orderService;
            this.orderLineRequestDtos = orderLines;
            this.latch = latch;
            this.errorLatch = errorLatch;
        }

        @Override
        public Order call() {
            try {
                orderService.createOrder(
                        orderLineRequestDtos
                );

            } catch (Exception e) {
                e.printStackTrace();
                errorLatch.countDown();

            } finally {
                latch.countDown();
            }

            return null;
        }
    }
}
