package com.alfsoftwares.honey.api.product.domain.model;

import com.alfsoftwares.honey.api.core.domain.model.BaseEntity;
import java.math.BigDecimal;

public class StockEntity extends BaseEntity {

  private ProductEntity product;
  private BigDecimal quantity;

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(final ProductEntity product) {
    this.product = product;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(final BigDecimal quantity) {
    this.quantity = quantity;
  }
}
