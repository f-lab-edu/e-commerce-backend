package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}