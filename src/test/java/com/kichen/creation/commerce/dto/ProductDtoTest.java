package com.kichen.creation.commerce.dto;

import com.kichen.creation.commerce.dto.product.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    void createDtoSuccess() {
        Long id = 0L;
        String name = "test";
        BigDecimal price = BigDecimal.valueOf(1.5);
        int stock = 0;

        ProductDto productDto = new ProductDto(
                id,
                name,
                price,
                stock
        );

        Assertions.assertThat(productDto.getId()).isEqualTo(id);
        Assertions.assertThat(productDto.getName()).isEqualTo(name);
        Assertions.assertThat(productDto.getPrice()).isEqualTo(price);
        Assertions.assertThat(productDto.getStock()).isEqualTo(stock);
    }

    @Test
    void createDtoNullId() {
        Long id = null;
        String name = "test";
        BigDecimal price = BigDecimal.valueOf(1.5);
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoNullName() {
        Long id = 0L;
        String name = null;
        BigDecimal price = BigDecimal.valueOf(1.5);
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoEmptyName() {
        Long id = 0L;
        String name = "";
        BigDecimal price = BigDecimal.valueOf(1.5);
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoNegativePrice() {
        Long id = 0L;
        String name = "test";
        BigDecimal price = BigDecimal.valueOf(-1);
        int stock = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

    @Test
    void createDtoNegativeStock() {
        Long id = 0L;
        String name = "test";
        BigDecimal price = BigDecimal.valueOf(1.5);
        int stock = -1;

        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductDto(id, name, price, stock)
        );
    }

}