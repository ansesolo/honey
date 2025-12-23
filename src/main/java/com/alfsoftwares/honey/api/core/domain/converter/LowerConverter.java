package com.alfsoftwares.honey.api.core.domain.converter;

import jakarta.persistence.Converter;

@Converter
public class LowerConverter extends StringFormatConverter {
  public LowerConverter() {
    super(String::toLowerCase);
  }
}
