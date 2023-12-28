package com.kichen.creation.commerce.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Getter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLineList = new ArrayList<>();

    private boolean isSuccessful;

    private LocalDateTime timeStamp;

    protected Order() {};

    public void addOrderLine(OrderLine orderLine) {
        orderLineList.add(orderLine);
        orderLine.updaterOrder(this);
    }
}
