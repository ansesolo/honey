package com.alfsoftwares.honey.api.stock.domain.port.out;

import com.alfsoftwares.honey.api.stock.domain.model.StockEntity;
import com.alfsoftwares.honey.api.stock.domain.model.StockMovementEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockGateway {

  void save(StockMovementEntity stockMovement);

  List<StockEntity> getStock();

  Optional<StockEntity> getStock(UUID unicity);
}
