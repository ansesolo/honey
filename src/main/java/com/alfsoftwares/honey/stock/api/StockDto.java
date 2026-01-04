package com.alfsoftwares.honey.stock.api;

import com.alfsoftwares.honey.core.domain.model.Flower;
import com.alfsoftwares.honey.core.domain.model.Unit;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record StockDto(
    UUID publicId,
    String createdBy,
    Instant createdAt,
    String modifiedBy,
    Instant modifiedAt,
    UUID productId,
    String productName,
    Unit productUnit,
    Integer stockYear,
    Flower flower,
    Integer quantity,
    BigDecimal averagePrice) {}
