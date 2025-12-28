package com.alfsoftwares.honey.api.stock.infrastructure.repository;

import com.alfsoftwares.honey.api.stock.domain.model.StockEntity;
import com.alfsoftwares.honey.api.stock.domain.model.StockMovementEntity;
import com.alfsoftwares.honey.api.stock.domain.port.out.StockGateway;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository implements StockGateway {

  private final Map<UUID, StockEntity> stockMovements = new HashMap<>();

  @Override
  public void save(final StockMovementEntity stockMovement) {
    UUID key = stockMovement.getProduct().getPublicId();
    BigDecimal quantity =
        stockMovement.getQuantity().multiply(new BigDecimal(stockMovement.getType().getSign()));
    StockEntity internal = stockMovements.get(key);
    if (internal == null) {
      internal = new StockEntity();
      internal.setProduct(stockMovement.getProduct());
      internal.setQuantity(quantity);
    } else {
      internal.setQuantity(internal.getQuantity().add(quantity));
    }
    stockMovements.put(key, internal);
  }

  @Override
  public List<StockEntity> getStock() {
    return stockMovements.values().stream().toList();
  }

  @Override
  public Optional<StockEntity> getStock(UUID unicity) {
    return Optional.ofNullable(stockMovements.get(unicity));
  }
}
