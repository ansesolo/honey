package com.alfsoftwares.honey.api.core.domain.converter;

import com.alfsoftwares.honey.api.product.domain.model.ProductAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class JsonMapConverter
    implements AttributeConverter<Map<ProductAttributes, Object>, String> {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Map<ProductAttributes, Object> attribute) {
    try {
      return attribute == null ? null : objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erreur de sérialisation JSON", e);
    }
  }

  @Override
  public Map<ProductAttributes, Object> convertToEntityAttribute(String dbData) {
    try {
      return dbData == null ? null : objectMapper.readValue(dbData, new TypeReference<Map<ProductAttributes, Object>>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Erreur de désérialisation JSON", e);
    }
  }
}
