package com.alfsoftwares.honey.core.infrastructure.configuration.converter;

import jakarta.persistence.Converter;

@Converter
public class UpperConverter extends StringFormatConverter {
  public UpperConverter() {
    super(String::toUpperCase);
  }
}
