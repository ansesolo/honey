package com.alfsoftwares.honey.stock.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.BaseEntity;
import com.alfsoftwares.honey.core.domain.model.Flower;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "stock")
public class StockEntity extends BaseEntity {

  private Integer stockYear;
  private UUID productId;

  @Enumerated(EnumType.STRING)
  private Flower flower;

  private Integer quantity = 0;
  private BigDecimal averagePrice = BigDecimal.ZERO;

  public StockEntity() {}

  public Integer getStockYear() {
    return stockYear;
  }

  public void setStockYear(final Integer stockYear) {
    this.stockYear = stockYear;
  }

  public UUID getProductId() {
    return productId;
  }

  public void setProductId(final UUID product) {
    this.productId = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public void setAveragePrice(final BigDecimal averagePrice) {
    this.averagePrice = averagePrice;
  }

  public Flower getFlower() {
    return flower;
  }

  public void setFlower(final Flower flower) {
    this.flower = flower;
  }
}
