package com.kichen.creation.commerce.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTest {

    OrderLine orderLine = mock();

    @Test
    void addOrderLineOrderSuccess() {
        Order order = new Order(true, LocalDateTime.now());
        when(orderLine.processOrder()).thenReturn(true);

        order.addOrderLine(orderLine);
        Assertions.assertThat(order.getOrderLineList().size()).isEqualTo(1);
        Assertions.assertThat(order.getOrderLineList()).contains(orderLine);
        assertTrue(order.isSuccessful());
    }

    @Test
    void addOrderLineOrderFail() {
        Order order = new Order(true, LocalDateTime.now());
        when(orderLine.processOrder()).thenReturn(false);

        order.addOrderLine(orderLine);
        Assertions.assertThat(order.getOrderLineList().size()).isEqualTo(1);
        Assertions.assertThat(order.getOrderLineList()).contains(orderLine);
        assertFalse(order.isSuccessful());
    }
}