package com.alfsoftwares.honey.api.core.domain.converter;

import jakarta.persistence.Converter;

@Converter
public class TitleCaseConverter extends StringFormatConverter {
  public TitleCaseConverter() {
    super(
        s -> {
          String[] words = s.split("\\s+");
          StringBuilder sb = new StringBuilder();
          for (String word : words) {
            if (!word.isEmpty()) {
              sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
          }
          return sb.toString().trim();
        });
  }
}
