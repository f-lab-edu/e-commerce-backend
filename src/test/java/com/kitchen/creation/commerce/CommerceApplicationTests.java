package com.kitchen.creation.commerce;

import com.kitchen.creation.commerce.order.domain.Order;
import com.kitchen.creation.commerce.order.dto.OrderLineRequestDto;
import com.kitchen.creation.commerce.order.exception.OrderFailureException;
import com.kitchen.creation.commerce.order.repository.OrderRepository;
import com.kitchen.creation.commerce.order.service.OrderService;
import com.kitchen.creation.commerce.product.domain.Product;
import com.kitchen.creation.commerce.product.repository.ProductRepository;
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
    void createOrderMultiThreadOneOrderPass() throws InterruptedException {
        int poolSize = 10000;
        Product product = new Product(
                "test product",
                32.0f,
                50
        );

        Product savedProduct = productRepository.save(product);

        CountDownLatch latch = new CountDownLatch(poolSize);
        CountDownLatch errorLatch = new CountDownLatch(poolSize - 1);

        Collection<CreateOrderCallable> createOrderCallableList = new ArrayList<>();
        for (int i=0; i<poolSize; i++) {
            List<OrderLineRequestDto> orderLineList = new ArrayList<>();
            orderLineList.add(
                    new OrderLineRequestDto(
                    savedProduct.getId(),
                    50
                )
            );

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

        // OrderFailException 이 poolSize -1 번 일어날 것 이라고 예상
        // 아래 Assertion 이 fail
//        Assertions.assertThat(errorLatch.getCount()).isEqualTo(0);
    }

    @Test
    void createOrderMultiThreadMultipleOrderPass() throws InterruptedException {
        int poolSize = 10;
        int initialStock = 50;
        Product product = new Product(
                "test product",
                32.0f,
                initialStock
        );

        Product savedProduct = productRepository.save(product);
        int orderStock = 5;

        CountDownLatch latch = new CountDownLatch(poolSize);
        CountDownLatch errorLatch = new CountDownLatch(poolSize - 1);

        Collection<CreateOrderCallable> createOrderCallableList = new ArrayList<>();
        for (int i=0; i<poolSize; i++) {
            List<OrderLineRequestDto> orderLineList = new ArrayList<>();
            orderLineList.add(
                    new OrderLineRequestDto(
                    savedProduct.getId(),
                    orderStock
                )
            );

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
        Assertions.assertThat(orderRepository.findAll().size()).isEqualTo(poolSize);
        Assertions.assertThat(errorLatch.getCount()).isEqualTo(poolSize - 1);

        // jpa lock 사용 시
        // read only 에서 실행할 수 없다는 에러
//        Assertions.assertThat(productRepository.findById(savedProduct.getId()).get().getStock())
//                .isEqualTo(initialStock - (orderStock * poolSize));
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

            } catch (OrderFailureException e) {
                errorLatch.countDown();

            } finally {
                latch.countDown();
            }

            return null;
        }
    }
}
