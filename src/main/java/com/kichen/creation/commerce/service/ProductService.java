package com.kichen.creation.commerce.service;

import com.kichen.creation.commerce.domain.Product;
import com.kichen.creation.commerce.exception.ProductNotFoundException;
import com.kichen.creation.commerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void editProduct(Product product) {
        try {
            if (productRepository.existsById(product.getId())) {
                productRepository.save(product);
            } else {
                throw new ProductNotFoundException("Product is not found!");
            }
        } catch (IllegalArgumentException e) {

            RuntimeException exception = new ProductNotFoundException("Given product Id is null!");
            exception.initCause(e);
            throw exception;
        }
    }

    public Product findProduct(Long id) {
        try {
            return productRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {

            RuntimeException exception = new ProductNotFoundException("Product is not found!");
            exception.initCause(e);
            throw exception;
        }
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
