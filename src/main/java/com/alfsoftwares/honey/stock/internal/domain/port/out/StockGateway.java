package com.alfsoftwares.honey.stock.internal.domain.port.out;

import com.alfsoftwares.honey.core.domain.model.Flower;
import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import com.alfsoftwares.honey.stock.internal.domain.model.StockMovementEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockGateway {

  void save(StockMovementEntity stockMovement);

  StockEntity save(StockEntity stock);

  List<StockEntity> getStock();

  Optional<StockEntity> getStock(UUID stockId);

  Optional<StockEntity> getStock(Integer year, UUID productId, Flower flower);

  void updateAveragePrice(UUID stockId);
}
