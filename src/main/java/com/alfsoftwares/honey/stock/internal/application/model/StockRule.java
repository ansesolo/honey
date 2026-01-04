package com.alfsoftwares.honey.stock.internal.application.model;

import com.alfsoftwares.honey.stock.internal.domain.model.MovementType;
import java.util.List;

public record StockRule(String id, RuleAttributes attributes) {

  public static StockRule fromValue(MovementType type) {
    RuleAttributes attributes =
        new RuleAttributes(
            type.getLabel(),
            type.getSign(),
            type.getCategories().stream().map(Enum::name).toList());
    return new StockRule(type.name(), attributes);
  }

  public record RuleAttributes(String label, int sign, List<String> categories) {}
}
