package com.alfsoftwares.honey.core.domain.model;

public enum Flower {
  ALL_FLOWERS("Toutes fleurs"),
  ACACIA("Acacia"),
  FOREST("Forêt"),
  FIR_TREE("Sapin"),
  SPRING("Printemps"),
  CHESTNUT_TREE("Châtaignier");

  private final String label;

  Flower(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }
}
