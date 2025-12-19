package com.alfsoftwares.honey.api.core.domain.model;

import org.jetbrains.annotations.NotNull;

public abstract class NamedEntity extends BaseEntity {

  @NotNull private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
