package com.kichen.creation.commerce.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void addOrderLine() {
        Order order = new Order();
        OrderLine orderLine = new OrderLine();

        order.addOrderLine(orderLine);
        Assertions.assertThat(order.getOrderLineList().size()).isEqualTo(1);
        Assertions.assertThat(order.getOrderLineList()).contains(orderLine);
    }
}