package com.alfsoftwares.honey.api.stock.domain.model;

import com.alfsoftwares.honey.api.product.domain.model.ProductCategory;
import java.util.Arrays;
import java.util.List;

public enum MovementType {
  PURCHASE(1, ProductCategory.EMPTY_JAR, ProductCategory.EMPTY_TRAY),
  HARVEST(1, ProductCategory.BULK_HONEY),
  PRODUCTION(1, ProductCategory.FULL_JAR, ProductCategory.FULL_TRAY, ProductCategory.QUEEN),
  INCREASE_STOCK(
      1,
      ProductCategory.EMPTY_JAR,
      ProductCategory.EMPTY_TRAY,
      ProductCategory.BULK_HONEY,
      ProductCategory.FULL_JAR,
      ProductCategory.FULL_TRAY,
      ProductCategory.QUEEN),
  DECREASE_STOCK(
      -1,
      ProductCategory.EMPTY_JAR,
      ProductCategory.EMPTY_TRAY,
      ProductCategory.BULK_HONEY,
      ProductCategory.FULL_JAR,
      ProductCategory.FULL_TRAY,
      ProductCategory.QUEEN),
  SALE(-1, ProductCategory.FULL_JAR, ProductCategory.FULL_TRAY, ProductCategory.QUEEN),
  LOSS(-1, ProductCategory.QUEEN),
  GIFT(-1, ProductCategory.FULL_JAR, ProductCategory.FULL_TRAY, ProductCategory.QUEEN);

  private final int sign;
  private final List<ProductCategory> categories;

  MovementType(final int sign, ProductCategory... categories) {
    this.sign = sign;
    this.categories = Arrays.asList(categories);
  }

  public int getSign() {
    return sign;
  }

  public List<ProductCategory> getCategories() {
    return categories;
  }
}
