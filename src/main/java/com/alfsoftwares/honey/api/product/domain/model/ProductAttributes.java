package com.alfsoftwares.honey.api.product.domain.model;

import java.util.Arrays;
import java.util.List;

public enum ProductAttributes {
  WEIGHT("Poids", ProductCategory.FULL_JAR, ProductCategory.BULK_HONEY),
  YEAR("Année", ProductCategory.FULL_JAR, ProductCategory.BULK_HONEY, ProductCategory.QUEEN),
  FLOWER("Type de fleur", ProductCategory.FULL_JAR, ProductCategory.BULK_HONEY);

  private final String label;
  private final List<ProductCategory> categories;

  ProductAttributes(String label, ProductCategory... categories) {
    this.label = label;
    this.categories = Arrays.asList(categories);
  }

  public List<ProductCategory> getCategories() {
    return categories;
  }

  @Override
  public String toString() {
    return label;
  }
}
