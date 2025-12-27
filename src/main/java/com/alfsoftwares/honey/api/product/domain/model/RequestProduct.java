package com.alfsoftwares.honey.api.product.domain.model;

import com.alfsoftwares.honey.api.core.domain.model.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public record RequestProduct(
    @NotBlank(message = "Name mandatory") String name,
    @NotNull(message = "Unit mandatory") Unit unit,
    BigDecimal defaultPrice,
    @NotNull(message = "Category mandatory") ProductCategory category,
    Map<ProductAttributes, Object> attributes) {

  public ProductEntity toEntity(ProductEntity dbEntity) {
    ProductEntity entity = new ProductEntity();
    if (dbEntity != null) {
      entity.setId(dbEntity.getId());
      entity.setPublicId(dbEntity.getPublicId());
    }
    entity.setName(name);
    entity.setUnit(unit);
    entity.setDefaultPrice(defaultPrice);
    entity.setCategory(category);
    entity.setAttributes(attributes);

    return entity;
  }

  public ProductEntity toEntity() {
    return toEntity(null);
  }
}
