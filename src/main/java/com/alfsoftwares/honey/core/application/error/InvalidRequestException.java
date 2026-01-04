package com.alfsoftwares.honey.core.application.error;

public class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(final String message) {
    super(message);
  }
}
