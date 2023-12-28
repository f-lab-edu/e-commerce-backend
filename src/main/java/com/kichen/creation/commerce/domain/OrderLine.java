package com.kichen.creation.commerce.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Getter
    private Order order;

    private int count;

    private Double totalPrice;

    protected OrderLine() {}

    public void updaterOrder(Order order) {
        this.order = order;
    }
}
