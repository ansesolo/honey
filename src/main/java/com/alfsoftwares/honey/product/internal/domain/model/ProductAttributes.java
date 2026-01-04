package com.alfsoftwares.honey.product.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.Category;
import java.util.Arrays;
import java.util.List;

public enum ProductAttributes {
  WEIGHT("Poids", Category.FULL_JAR, Category.BULK_HONEY),
  YEAR("Année", Category.FULL_JAR, Category.BULK_HONEY, Category.QUEEN),
  FLOWER("Type de fleur", Category.FULL_JAR, Category.BULK_HONEY);

  private final String label;
  private final List<Category> categories;

  ProductAttributes(String label, Category... categories) {
    this.label = label;
    this.categories = Arrays.asList(categories);
  }

  public List<Category> getCategories() {
    return categories;
  }

  @Override
  public String toString() {
    return label;
  }
}
