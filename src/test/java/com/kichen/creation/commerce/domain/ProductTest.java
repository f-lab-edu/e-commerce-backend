package com.kichen.creation.commerce.domain;

import com.kichen.creation.commerce.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    Product product = new Product();

    @Test
    void addStockPositive() {
        int quantity = 3;
        product.addStock(quantity);
        Assertions.assertThat(product.getStock()).isEqualTo(quantity);
    }

    @Test
    void addStockNegative() {
        int quantity = -1;
        assertThrows(IllegalArgumentException.class, () -> product.addStock(quantity));
    }

    @Test
    void removeStockSuccess() {
        int quantityAdd = 3;
        product.addStock(quantityAdd);
        int quantityRemove = 2;
        product.removeStock(quantityRemove);
        Assertions.assertThat(product.getStock()).isEqualTo(quantityAdd - quantityRemove);
    }

    @Test
    void removeStockFailure() {
        int quantityAdd = 3;
        product.addStock(quantityAdd);
        int quantityRemove = 4;
        assertThrows(NotEnoughStockException.class, () -> product.removeStock(quantityRemove));
    }

    @Test
    void removeStockNegative() {
        int quantity = -1;
        assertThrows(IllegalArgumentException.class, () -> product.removeStock(quantity));
    }
}