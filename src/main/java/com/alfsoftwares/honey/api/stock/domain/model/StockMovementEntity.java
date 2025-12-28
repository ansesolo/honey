package com.alfsoftwares.honey.api.stock.domain.model;

import com.alfsoftwares.honey.api.core.domain.model.BaseEntity;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import java.math.BigDecimal;

public class StockMovementEntity extends BaseEntity {

  private ProductEntity product;
  private BigDecimal quantity;
  private MovementType type;
  private String reason;

  public StockMovementEntity() {}

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

  public MovementType getType() {
    return type;
  }

  public void setType(final MovementType type) {
    this.type = type;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(final String reason) {
    this.reason = reason;
  }
}
