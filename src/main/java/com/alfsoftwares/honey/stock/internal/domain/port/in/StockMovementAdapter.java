package com.alfsoftwares.honey.stock.internal.domain.port.in;

import com.alfsoftwares.honey.stock.internal.application.model.StockMovementRequest;
import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import java.util.List;
import java.util.UUID;

public interface StockMovementAdapter {
  void createStockMovement(final StockMovementRequest stockMovement);

  List<StockEntity> getStock();

  StockEntity getStock(UUID uuid);
}
