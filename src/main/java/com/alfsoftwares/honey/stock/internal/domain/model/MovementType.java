package com.alfsoftwares.honey.stock.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.Category;
import java.util.Arrays;
import java.util.List;

public enum MovementType {
  PURCHASE("Achat", 1, Category.EMPTY_JAR, Category.EMPTY_TRAY),
  HARVEST("Récolte", 1, Category.BULK_HONEY),
  PRODUCTION("Production", 1, Category.FULL_JAR, Category.FULL_TRAY, Category.QUEEN),
  INCREASE_STOCK(
      "Augmentation stock",
      1,
      Category.EMPTY_JAR,
      Category.EMPTY_TRAY,
      Category.BULK_HONEY,
      Category.FULL_JAR,
      Category.FULL_TRAY,
      Category.QUEEN),
  DECREASE_STOCK(
      "Diminution stock",
      -1,
      Category.EMPTY_JAR,
      Category.EMPTY_TRAY,
      Category.BULK_HONEY,
      Category.FULL_JAR,
      Category.FULL_TRAY,
      Category.QUEEN),
  SALE("Vente", -1, Category.FULL_JAR, Category.FULL_TRAY, Category.QUEEN),
  LOSS("Perte", -1, Category.QUEEN),
  GIFT("Cadeau", -1, Category.FULL_JAR, Category.FULL_TRAY, Category.QUEEN);

  private final String label;
  private final int sign;
  private final List<Category> categories;

  MovementType(final String label, final int sign, Category... categories) {
    this.label = label;
    this.sign = sign;
    this.categories = Arrays.asList(categories);
  }

  public int getSign() {
    return sign;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public String getLabel() {
    return label;
  }
}
