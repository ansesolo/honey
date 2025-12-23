package com.alfsoftwares.honey.api.customer.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
  public CustomerEntity toEntity(Long id) {
    CustomerEntity entity = new CustomerEntity();
    if (id != null) {
      entity.setId(id);
    }
    entity.setFirstname(firstname);
    entity.setLastname(lastname);
    entity.setEmail(email);
    entity.setPhone(phone);
    entity.setStreet(street);
    entity.setPostalCode(postalCode);
    entity.setCity(city);

    return entity;
  }

  public CustomerEntity toEntity() {
    return toEntity(null);
  }
}
