package com.alfsoftwares.honey.core.domain.model;

public enum Category {
  EMPTY_JAR("Pot vide"),
  FULL_JAR("Pot plein"),
  EMPTY_TRAY("Barquette vide"),
  FULL_TRAY("Barquette"),
  BULK_HONEY("Miel vrac"),
  QUEEN("Reine");

  private final String label;

  Category(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }
}
