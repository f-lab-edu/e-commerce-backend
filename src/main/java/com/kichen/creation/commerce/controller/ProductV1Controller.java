package com.kichen.creation.commerce.controller;

import com.kichen.creation.commerce.dto.product.CreateProductDto;
import com.kichen.creation.commerce.dto.product.ProductDto;
import com.kichen.creation.commerce.response.SuccessResponse;
import com.kichen.creation.commerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductV1Controller {

    private final ProductService productService;

    @GetMapping("products/all")
    public ResponseEntity<SuccessResponse<List<ProductDto>>> getAllProducts() {
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.value(), productService.findAllProducts()));
    }

    @GetMapping("product")
    public ResponseEntity<ProductDto> getProduct(@RequestParam Long id) {
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @PostMapping("products/create")
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody @Valid CreateProductDto createProductDto) {
        return ResponseEntity.ok(productService.createProduct(createProductDto));
    }

    @PutMapping("product/edit")
    public void editProduct(@RequestBody @Valid ProductDto productDto) {
        productService.editProduct(productDto);
    }
}
