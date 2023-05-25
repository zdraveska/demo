package com.example.demo.domain.exceptions;

public class EmailException extends RuntimeException {

  public EmailException(String message) {
    super(String.format("%s", message));
  }
}
