package com.kichen.creation.commerce.domain;

import com.kichen.creation.commerce.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Getter
    private Long id;

    private String name;

    private Double price;

    @Getter
    private int stock;

    public void addStock(int quantity) {
        validateQuantityPositive(quantity);
        stock += quantity;
    }

    public void removeStock(int quantity) {
        validateQuantityPositive(quantity);
        if (quantity > stock) {
            throw new NotEnoughStockException("Quantity argument is greater than the remaining stock!");
        }
        stock -= quantity;
    }

    private void validateQuantityPositive(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity argument has to be positive!");
        }
    }

}
