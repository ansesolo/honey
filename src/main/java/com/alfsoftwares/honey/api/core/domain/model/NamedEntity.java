package com.alfsoftwares.honey.api.core.domain.model;

import com.alfsoftwares.honey.api.core.domain.converter.TitleCaseConverter;
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
