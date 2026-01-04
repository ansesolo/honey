package com.alfsoftwares.honey.core.infrastructure.configuration.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneConverter implements AttributeConverter<String, String> {

  @Override
  public String convertToDatabaseColumn(String attribute) {
    if (attribute == null || attribute.isBlank()) {
      return attribute;
    }
    String clean = attribute.replaceAll("\\D", "");
    if (clean.length() == 10) {
      return String.join(".", clean.split("(?<=\\G\\d{2})"));
    }

    return attribute;
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return dbData;
  }
}
