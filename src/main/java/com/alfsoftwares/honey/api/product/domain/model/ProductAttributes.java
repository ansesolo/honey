package com.alfsoftwares.honey.api.product.domain.model;

import java.util.Arrays;
import java.util.List;

public enum ProductAttributes {
  WEIGHT(ProductCategory.FULL_JAR, ProductCategory.BULK_HONEY),
  SIZE(ProductCategory.EMPTY_JAR, ProductCategory.EMPTY_JAR),
  YEAR(ProductCategory.FULL_JAR, ProductCategory.BULK_HONEY, ProductCategory.QUEEN),
  FLOWER(ProductCategory.FULL_JAR, ProductCategory.BULK_HONEY);

  private final List<ProductCategory> categories;

  ProductAttributes(ProductCategory... categories) {
    this.categories = Arrays.asList(categories);
  }

  public List<ProductCategory> getCategories() {
    return categories;
  }
}
