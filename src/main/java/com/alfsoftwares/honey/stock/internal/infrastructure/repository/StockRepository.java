package com.alfsoftwares.honey.stock.internal.infrastructure.repository;

import com.alfsoftwares.honey.core.domain.model.Flower;
import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import com.alfsoftwares.honey.stock.internal.domain.model.StockMovementEntity;
import com.alfsoftwares.honey.stock.internal.domain.port.out.StockGateway;
import com.alfsoftwares.honey.stock.internal.infrastructure.repository.jpa.StockJpaRepository;
import com.alfsoftwares.honey.stock.internal.infrastructure.repository.jpa.StockMovementJpaRepository;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository implements StockGateway {

  private final StockJpaRepository jpa;
  private final StockMovementJpaRepository jpaMovement;

  public StockRepository(
      final StockJpaRepository jpa, final StockMovementJpaRepository jpaMovement) {
    this.jpa = jpa;
    this.jpaMovement = jpaMovement;
  }

  @Override
  public void save(final StockMovementEntity stockMovement) {
    jpaMovement.save(stockMovement);
  }

  @Override
  public StockEntity save(final StockEntity stock) {
    return jpa.save(stock);
  }

  @Override
  public List<StockEntity> getStock() {
    return jpa.findAll();
  }

  @Override
  public Optional<StockEntity> getStock(final UUID stockId) {
    return jpa.findByPublicId(stockId);
  }

  @Override
  public Optional<StockEntity> getStock(Integer year, UUID productId, Flower flower) {
    return jpa.findByStockYearAndProductIdAndFlower(year, productId, flower);
  }

  @Override
  public void updateAveragePrice(UUID stockId) {
    jpa.updateAveragePriceFromMovements(stockId);
  }
}
