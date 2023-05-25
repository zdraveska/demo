package com.example.demo.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(Long id) {
    super(String.format("Post with id: %d not found", id));
  }

}

