package com.alfsoftwares.honey.api.product.application.model;

import com.alfsoftwares.honey.api.product.domain.model.MovementType;
import java.math.BigDecimal;
import java.util.UUID;

public record StockMovementRequest(
    UUID productId, BigDecimal quantity, MovementType type, String reason) {}
