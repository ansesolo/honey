package com.alfsoftwares.honey.product.api;

import com.alfsoftwares.honey.core.domain.model.Category;
import com.alfsoftwares.honey.core.domain.model.Flower;
import com.alfsoftwares.honey.core.domain.model.Unit;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductDto(
    UUID publicId,
    String createdBy,
    Instant createdAt,
    String modifiedBy,
    Instant modifiedAt,
    String name,
    Unit unit,
    BigDecimal defaultPrice,
    Category category,
    Flower flower,
    Integer weight) {

  public String getFullName() {
    if (flower == null || flower.toString() == null) {
      return name;
    }
    return String.format("%s (%s)", name, flower);
  }
}
