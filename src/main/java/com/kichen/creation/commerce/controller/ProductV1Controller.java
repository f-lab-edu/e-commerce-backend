package com.kichen.creation.commerce.controller;

import com.kichen.creation.commerce.dto.product.ProductDto;
import com.kichen.creation.commerce.dto.product.ProductResponseDto;
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

    @GetMapping("/products")
    public ResponseEntity<SuccessResponse<List<ProductResponseDto>>> getAllProducts() {
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.value(), productService.findAllProducts()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<SuccessResponse<ProductResponseDto>> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.value(), productService.findProduct(id)));
    }

    @PostMapping("/products")
    public ResponseEntity<?> createNewProduct(@RequestBody @Valid ProductDto productDto) {
       productService.createProduct(productDto);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        productService.editProduct(id, productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
