package com.alfsoftwares.honey.product.internal.application;

import com.alfsoftwares.honey.product.api.ProductDto;
import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;

public class ProductMapper {

  public static ProductDto fromEntityToDto(ProductEntity entity) {
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
