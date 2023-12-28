package com.kichen.creation.commerce.domain;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private Double price;

    private int stock;

    protected Product() {}
}
