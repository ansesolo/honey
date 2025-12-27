package com.alfsoftwares.honey.api.product.domain.model;

public enum ProductCategory {
  EMPTY_JAR("Pot vide"),
  FULL_JAR("Pot plein"),
  EMPTY_TRAY("Barquette vide"),
  FULL_TRAY("Barquette"),
  BULK_HONEY("Miel vrac"),
  QUEEN("Reine");

  private final String label;

  ProductCategory(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }
}
