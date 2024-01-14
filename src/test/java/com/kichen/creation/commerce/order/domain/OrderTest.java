package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.order.dto.OrderLineDto;
import com.kichen.creation.commerce.product.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderTest {

    Product product = mock();
    int testCount = 10;
    OrderLine orderLine = new OrderLine(product, testCount);

    @Test
    void createOrderSuccess() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        when(product.hasEnoughStock(testCount)).thenReturn(true);
        Order order = Order.createOrder(orderLines);

        assertTrue(order.isSuccessful());
    }

    @Test
    void createOrderFail() {
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        when(product.hasEnoughStock(testCount)).thenReturn(false);
        Order order = Order.createOrder(orderLines);

        assertFalse(order.isSuccessful());
    }

    @Test
    void createOrderMultiThreadAccess() {
        int poolSize = 5;
        List<OrderLine> orderLines = new ArrayList<>();
        Product testProduct = new Product(0L, "test", 15f, 10);
        orderLines.add(new TestOrderLine(testProduct, testCount));
        CountDownLatch latch = new CountDownLatch(poolSize);

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i=0; i<poolSize; i++) {
            executorService.submit(() -> {
                try {
                    Order.createOrder(orderLines);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        try { latch.await(); } catch (InterruptedException e) {}
    }
}