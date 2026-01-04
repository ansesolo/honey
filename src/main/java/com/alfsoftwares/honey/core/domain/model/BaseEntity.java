package com.alfsoftwares.honey.core.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** Entité de base. On va gérer ici tout ce qui est commun aux entités */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private long id;

  @Column(unique = true, nullable = false)
  private UUID publicId = UUID.randomUUID();

  @CreatedBy
  @Column(nullable = false, updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedBy
  @Column(insertable = false)
  private String modifiedBy;

  @LastModifiedDate
  @Column(insertable = false)
  private Instant modifiedAt;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(final UUID publicId) {
    this.publicId = publicId;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final Instant createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(final String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(final Instant modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
}
