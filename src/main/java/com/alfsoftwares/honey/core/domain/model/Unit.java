package com.alfsoftwares.honey.core.domain.model;

public enum Unit {
  UNIT("Unité"),
  GRAM("Gramme");

  private final String label;

  Unit(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }
}
