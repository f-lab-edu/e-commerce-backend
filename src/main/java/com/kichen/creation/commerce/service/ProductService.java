package com.kichen.creation.commerce.service;

import com.kichen.creation.commerce.domain.Product;
import com.kichen.creation.commerce.dto.product.CreateProductDto;
import com.kichen.creation.commerce.dto.product.ProductDto;
import com.kichen.creation.commerce.exception.product.ProductNotFoundException;
import com.kichen.creation.commerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
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
    public ProductDto createProduct(@NonNull CreateProductDto createProductDto) {
        Product product = new Product(
                createProductDto.getName(),
                createProductDto.getPrice().floatValue(),
                createProductDto.getStock()
        );

        return productRepository.save(product).toProductDto();
    }

    @Transactional
    public void editProduct(@NonNull ProductDto productDto) {
        Product product = findProductFromRepository(productDto.getId());
        product.updateFromDto(productDto);
        productRepository.save(product);
    }

    public ProductDto findProduct(@NonNull Long id) {
        return findProductFromRepository(id).toProductDto();
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(Product::toProductDto).toList();
    }

    private Product findProductFromRepository(@NonNull Long id) {
        try {
            return productRepository.getReferenceById(id);

        } catch (EntityNotFoundException e) {
            throw new ProductNotFoundException("Product is not found!", e);
        }
    }
}
