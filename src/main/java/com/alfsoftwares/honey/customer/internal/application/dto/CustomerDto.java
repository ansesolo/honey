package com.alfsoftwares.honey.customer.internal.application.dto;

import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import java.time.Instant;
import java.util.UUID;

public record CustomerDto(
    UUID publicId,
    String createdBy,
    Instant createdAt,
    String modifiedBy,
    Instant modifiedAt,
    String firstname,
    String lastname,
    String email,
    String phone,
    String street,
    String postalCode,
    String city) {

  public static CustomerDto fromEntity(CustomerEntity entity) {
    return new CustomerDto(
        entity.getPublicId(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getModifiedBy(),
        entity.getModifiedAt(),
        entity.getFirstname(),
        entity.getLastname(),
        entity.getEmail(),
        entity.getPhone(),
        entity.getStreet(),
        entity.getPostalCode(),
        entity.getCity());
  }
}
