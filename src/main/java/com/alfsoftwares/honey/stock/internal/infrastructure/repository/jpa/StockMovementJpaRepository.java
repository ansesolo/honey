package com.alfsoftwares.honey.stock.internal.infrastructure.repository.jpa;

import com.alfsoftwares.honey.stock.internal.domain.model.StockMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementJpaRepository extends JpaRepository<StockMovementEntity, Long> {}
