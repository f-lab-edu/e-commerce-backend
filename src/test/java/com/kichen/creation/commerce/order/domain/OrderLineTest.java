package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderLineTest {

    Product product = mock();

    Order order = mock();

    int testCount = 10;

    @Test
    void createOrderSuccess() {
        OrderLine orderLine = new OrderLine(product, testCount);
        when(product.hasEnoughStock(testCount)).thenReturn(true);

        orderLine.createOrder(order);
        Assertions.assertThat(orderLine.getOrder()).isEqualTo(order);
        verify(product).removeStock(testCount);
    }

    @Test
    void createOrderFail() {
        OrderLine orderLine = new OrderLine(product, testCount);
        when(product.hasEnoughStock(testCount)).thenReturn(false);

        orderLine.createOrder(order);
        Assertions.assertThat(orderLine.getOrder()).isEqualTo(order);
        verify(product, never()).removeStock(testCount);
        verify(order).failOrder();
    }
}