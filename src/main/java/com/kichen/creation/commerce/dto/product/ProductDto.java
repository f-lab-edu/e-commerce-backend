package com.kichen.creation.commerce.dto.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProductDto {

    @NotNull(message = "Invalid Product Id: Id is Null")
    private final Long id;

    @NotBlank(message = "Invalid Product Name: Name is Blank")
    @NotEmpty(message = "Invalid Product Name: Name is Empty")
    @NotNull(message = "Invalid Product Name: Name is Null")
    private final String name;

    @Min(value = 1, message = "Invalid Price: Equals to zero or Less than zero")
    @Digits(integer = 10, fraction = 2, message = "Invalid price: digit out of range")
    private final BigDecimal price;

    @Min(value = 0, message = "Invalid Stock: Equals to zero or Less than zero")
    @Digits(integer = 10, fraction = 0, message = "Invalid price: digit out of range")
    private final int stock;
}
