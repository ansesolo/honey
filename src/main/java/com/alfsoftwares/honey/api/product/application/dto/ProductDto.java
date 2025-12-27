package com.alfsoftwares.honey.api.product.application.dto;

import com.alfsoftwares.honey.api.core.domain.model.Unit;
import com.alfsoftwares.honey.api.product.domain.model.ProductAttributes;
import com.alfsoftwares.honey.api.product.domain.model.ProductCategory;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
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
    ProductCategory category,
    Map<ProductAttributes, Object> attributes) {

  public static ProductDto fromEntity(ProductEntity entity) {
    return new ProductDto(
        entity.getPublicId(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getModifiedBy(),
        entity.getModifiedAt(),
        entity.getName(),
        entity.getUnit(),
        entity.getDefaultPrice(),
        entity.getCategory(),
        entity.getAttributes());
  }
}
