package com.alfsoftwares.honey.api.product.application.model;

import com.alfsoftwares.honey.api.product.domain.model.MovementType;
import java.math.BigDecimal;

public record StockMovementRequest(
    long productId, BigDecimal quantity, MovementType type, String reason) {}
