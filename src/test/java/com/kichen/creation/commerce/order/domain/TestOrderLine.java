package com.kichen.creation.commerce.order.domain;

import com.kichen.creation.commerce.product.domain.Product;

public class TestOrderLine extends OrderLine {
    private Product product;

    private Order order;

    private int count;

    public TestOrderLine(Product product, int count) {
        super(product, count);
        this.product = product;
        this.count = count;
    }

    @Override
    public void createOrder(Order order) {
        this.order = order;
        if (product.hasEnoughStock(count)) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            product.removeStock(count);
        } else {
            order.failOrder();
        }
    }
}
