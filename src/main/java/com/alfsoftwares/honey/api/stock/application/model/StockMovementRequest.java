package com.alfsoftwares.honey.api.stock.application.model;

import com.alfsoftwares.honey.api.stock.domain.model.MovementType;
import java.math.BigDecimal;
import java.util.UUID;

public record StockMovementRequest(
        UUID productId, BigDecimal quantity, MovementType type, String reason) {}
