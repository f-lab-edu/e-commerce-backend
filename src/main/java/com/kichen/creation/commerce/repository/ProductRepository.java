package com.kichen.creation.commerce.repository;

import com.kichen.creation.commerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    List<Product> findAll();

    @Override
    Product getReferenceById(Long id);

    @Override
    <S extends Product> S save(S product);

    @Override
    boolean existsById(Long id);
}
