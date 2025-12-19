package com.alfsoftwares.honey.api.core.application.error;

public class NotFoundException extends RuntimeException {
  public NotFoundException(final String message) {
    super(message);
  }
}
