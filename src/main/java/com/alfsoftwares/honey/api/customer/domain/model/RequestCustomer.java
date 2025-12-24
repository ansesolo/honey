package com.alfsoftwares.honey.api.customer.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;

public record RequestCustomer(
    @NotBlank(message = "Firstname mandatory") String firstname,
    @NotBlank(message = "Lastname mandatory") String lastname,
    @Email(message = "invalid email") String email,
    @Pattern(
            regexp = "^$|^\\d{10}$|^(\\d{2}\\.){4}\\d{2}$",
            message = "Phone must contains 10 digits")
        String phone,
    String street,
    @Pattern(regexp = "^$|\\d{5}", message = "Postal code must contains 5 digits")
        String postalCode,
    String city) {
  public CustomerEntity toEntity(CustomerEntity dbEntity) {
    CustomerEntity entity = new CustomerEntity();
    if (dbEntity != null) {
      entity.setId(dbEntity.getId());
      entity.setPublicId(dbEntity.getPublicId());
    }
    entity.setFirstname(firstname);
    entity.setLastname(lastname);
    entity.setEmail(StringUtils.isBlank(email) ? null : email);
    entity.setPhone(StringUtils.isBlank(phone) ? null : phone);
    entity.setStreet(StringUtils.isBlank(street) ? null : phone);
    entity.setPostalCode(StringUtils.isBlank(postalCode) ? null : postalCode);
    entity.setCity(StringUtils.isBlank(city) ? null : city);

    return entity;
  }

  public CustomerEntity toEntity() {
    return toEntity(null);
  }
}
