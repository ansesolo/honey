package com.alfsoftwares.honey.stock.internal.application;

import com.alfsoftwares.honey.product.api.ProductDto;
import com.alfsoftwares.honey.product.api.ProductServiceApi;
import com.alfsoftwares.honey.stock.api.StockDto;
import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import org.springframework.stereotype.Service;

@Service
public class StockMapper {

  private final ProductServiceApi productApi;

  public StockMapper(final ProductServiceApi productApi) {
    this.productApi = productApi;
  }

  public StockDto fromEntityToDto(StockEntity entity) {

    ProductDto product = productApi.getProduct(entity.getProductId());

    return new StockDto(
        entity.getPublicId(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getModifiedBy(),
        entity.getModifiedAt(),
        entity.getProductId(),
        product.getFullName(),
        product.unit(),
        entity.getStockYear(),
        entity.getQuantity(),
        entity.getAveragePrice());
  }
}
