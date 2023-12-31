package com.kichen.creation.commerce.service;

import com.kichen.creation.commerce.domain.Product;
import com.kichen.creation.commerce.exception.ProductNotFoundException;
import com.kichen.creation.commerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    ProductRepository productRepository = mock();
    ProductService productService = new ProductService(productRepository);
    Product testProduct = mock();
    Long testId = 1L;

    @Test
    void createProduct() {
        productService.createProduct(testProduct);
        verify(productRepository).save(testProduct);
    }

    @Test
    void editProduct() {
        when(testProduct.getId()).thenReturn(testId);
        when(productRepository.existsById(testId)).thenReturn(true);
        productService.editProduct(testProduct);

        verify(productRepository).save(testProduct);
    }

    @Test
    void editProductNullProductId() {
        when(testProduct.getId()).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> productService.editProduct(testProduct));
    }

    @Test
    void editProductDoesNotExist() {
        when(testProduct.getId()).thenReturn(testId);
        when(productRepository.existsById(testId)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.editProduct(testProduct));
    }

    @Test
    void findProduct() {
        Long testId = 1L;
        productService.findProduct(testId);
        verify(productRepository).getReferenceById(testId);
    }

    @Test
    void findProductDoesNotExist() {
        when(productRepository.getReferenceById(testId)).thenThrow(EntityNotFoundException.class);
        assertThrows(ProductNotFoundException.class, () -> productService.findProduct(testId));
    }

    @Test
    void findAllProducts() {
        productService.findAllProducts();
        verify(productRepository).findAll();
    }
}