package com.alfsoftwares.honey.api.core.application.error;

public class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(final String message) {
    super(message);
  }
}
