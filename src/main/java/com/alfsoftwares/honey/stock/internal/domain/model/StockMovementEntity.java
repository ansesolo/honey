package com.alfsoftwares.honey.stock.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "stock_movement")
public class StockMovementEntity extends BaseEntity {

  private LocalDate movementDate;
  private UUID stockId;

  @Enumerated(EnumType.STRING)
  private MovementType movementType;

  private Integer quantity;
  private BigDecimal price;
  private String reason;

  public StockMovementEntity() {}

  public LocalDate getMovementDate() {
    return movementDate;
  }

  public void setMovementDate(final LocalDate movementDate) {
    this.movementDate = movementDate;
  }

  public UUID getStockId() {
    return stockId;
  }

  public void setStockId(final UUID stockId) {
    this.stockId = stockId;
  }

  public MovementType getMovementType() {
    return movementType;
  }

  public void setMovementType(final MovementType movementType) {
    this.movementType = movementType;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(final String reason) {
    this.reason = reason;
  }
}
