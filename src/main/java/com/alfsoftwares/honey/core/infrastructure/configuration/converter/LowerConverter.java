package com.alfsoftwares.honey.core.infrastructure.configuration.converter;

import jakarta.persistence.Converter;

@Converter
public class LowerConverter extends StringFormatConverter {
  public LowerConverter() {
    super(String::toLowerCase);
  }
}
