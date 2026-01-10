package com.alfsoftwares.honey.core.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

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

  @JsonCreator
  public static Flower fromString(String value) {
    if (StringUtils.isEmpty(value)) {
      return null;
    }
    return Flower.valueOf(value);
  }

  @Override
  public String toString() {
    return label;
  }
}
