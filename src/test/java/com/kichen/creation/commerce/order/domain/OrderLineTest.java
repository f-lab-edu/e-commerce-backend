package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderLineTest {

    Product product = mock();

    @Test
    void updaterOrder() {
        OrderLine orderLine = new OrderLine();
        Order order = new Order();

        orderLine.createOrder(order);
        Assertions.assertThat(orderLine.getOrder()).isEqualTo(order);
    }

    @Test
    void processOrderSuccess() {
        int testCount = 10;
        when(product.hasEnoughStock(testCount)).thenReturn(true);
        OrderLine orderLine = new OrderLine(product, testCount);
        assertTrue(orderLine.processOrder());
    }

    @Test
    void processOrderFail() {
        int testCount = 10;
        when(product.hasEnoughStock(testCount)).thenReturn(false);
        OrderLine orderLine = new OrderLine(product, testCount);
        assertFalse(orderLine.processOrder());
    }
}