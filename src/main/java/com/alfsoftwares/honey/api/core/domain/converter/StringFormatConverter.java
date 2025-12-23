package com.alfsoftwares.honey.api.core.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.function.UnaryOperator;

@Converter
public class StringFormatConverter implements AttributeConverter<String, String> {
  private final UnaryOperator<String> formatAction;

  protected StringFormatConverter(UnaryOperator<String> action) {
    this.formatAction = action;
  }

  @Override
  public String convertToDatabaseColumn(String attribute) {
    return (attribute != null) ? formatAction.apply(attribute) : null;
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return dbData;
  }
}
