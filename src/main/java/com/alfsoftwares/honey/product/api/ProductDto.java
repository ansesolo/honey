package com.alfsoftwares.honey.product.api;

import com.alfsoftwares.honey.core.domain.model.Category;
import com.alfsoftwares.honey.core.domain.model.Unit;
import com.alfsoftwares.honey.product.internal.domain.model.ProductAttributes;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record ProductDto(
    UUID publicId,
    String createdBy,
    Instant createdAt,
    String modifiedBy,
    Instant modifiedAt,
    String name,
    Unit unit,
    BigDecimal defaultPrice,
    Category category,
    Map<ProductAttributes, Object> attributes) {}
