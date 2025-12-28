package com.alfsoftwares.honey.api.stock.domain.port.in;

import com.alfsoftwares.honey.api.stock.application.model.StockMovementRequest;
import com.alfsoftwares.honey.api.stock.domain.model.StockEntity;
import java.util.List;
import java.util.UUID;

public interface StockMovementAdapter {
  void createStockMovement(final StockMovementRequest stockMovement);

  List<StockEntity> getStock();

  StockEntity getStock(UUID uuid);
}
