package com.alfsoftwares.honey.api.core.domain.model;

import jakarta.annotation.Generated;
import java.time.ZonedDateTime;

/** Entité de base. On va gérer ici tout ce qui est commun aux entités */
public abstract class BaseEntity {

  @Generated({})
  private long id;

  private String createdBy;
  private ZonedDateTime createdAt;
  private String modifiedBy;
  private ZonedDateTime modifiedAt;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(final String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public ZonedDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(final ZonedDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
}
