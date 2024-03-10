package com.kitchen.creation.commerce.auth.repository;

import com.kitchen.creation.commerce.auth.Token;
import org.springframework.data.repository.CrudRepository;


public interface TokenRepository extends CrudRepository<Token, String> {}
