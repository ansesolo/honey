package com.alfsoftwares.honey.stock.internal.infrastructure.repository.jpa;

import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {

  Optional<StockEntity> findByPublicId(UUID uuid);

  Optional<StockEntity> findByStockYearAndProductId(Integer year, UUID productId);

  @Modifying
  @Query(
"""
      UPDATE StockEntity s SET s.averagePrice =
          (SELECT AVG(sm.price) FROM StockMovementEntity sm WHERE sm.stockId = s.publicId)
          WHERE s.publicId = :stockId
""")
  void updateAveragePriceFromMovements(@Param("stockId") UUID stockId);
}
