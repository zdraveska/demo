package com.example.demo.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long id) {
    super(String.format("Product with id: %d not found", id));
  }

}

