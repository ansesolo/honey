package com.alfsoftwares.honey.api.core.domain.converter;

import jakarta.persistence.Converter;

@Converter
public class UpperConverter extends StringFormatConverter {
  public UpperConverter() {
    super(String::toUpperCase);
  }
}
