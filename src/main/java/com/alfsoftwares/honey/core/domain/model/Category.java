package com.alfsoftwares.honey.core.domain.model;

public enum Category {
  EMPTY_JAR("Pot vide", false),
  FULL_JAR("Pot plein", true),
  EMPTY_TRAY("Barquette vide", false),
  FULL_TRAY("Barquette", true),
  BULK_HONEY("Miel vrac", true),
  QUEEN("Reine", false);

  private final String label;
  private final boolean needFlower;

  Category(final String label, boolean needFlower) {
    this.label = label;
    this.needFlower = needFlower;
  }

  public boolean isNeedFlower() {
    return needFlower;
  }

  @Override
  public String toString() {
    return label;
  }
}
