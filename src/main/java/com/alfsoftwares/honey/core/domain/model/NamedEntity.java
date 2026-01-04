package com.alfsoftwares.honey.core.domain.model;

import com.alfsoftwares.honey.core.infrastructure.configuration.converter.TitleCaseConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class NamedEntity extends BaseEntity {

  @NotNull
  @Convert(converter = TitleCaseConverter.class)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
