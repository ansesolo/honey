package com.alfsoftwares.honey.stock.internal.application.model;

import com.alfsoftwares.honey.stock.internal.domain.model.MovementType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record StockMovementRequest(
    LocalDate movementDate,
    UUID productId,
    Integer quantity,
    MovementType type,
    BigDecimal price,
    String reason) {}
