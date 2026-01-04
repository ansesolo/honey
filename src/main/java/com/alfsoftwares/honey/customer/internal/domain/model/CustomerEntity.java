package com.alfsoftwares.honey.customer.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.BaseEntity;
import com.alfsoftwares.honey.core.infrastructure.configuration.converter.LowerConverter;
import com.alfsoftwares.honey.core.infrastructure.configuration.converter.PhoneConverter;
import com.alfsoftwares.honey.core.infrastructure.configuration.converter.TitleCaseConverter;
import com.alfsoftwares.honey.core.infrastructure.configuration.converter.UpperConverter;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity extends BaseEntity {

  @Convert(converter = TitleCaseConverter.class)
  private String firstname;

  @Convert(converter = UpperConverter.class)
  private String lastname;

  @Convert(converter = LowerConverter.class)
  @Column(unique = true)
  private String email;

  @Convert(converter = PhoneConverter.class)
  private String phone;

  @Convert(converter = TitleCaseConverter.class)
  private String street;

  private String postalCode;

  @Convert(converter = UpperConverter.class)
  private String city;

  public CustomerEntity() {}

  @Nonnull
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(@Nonnull final String firstname) {
    this.firstname = firstname;
  }

  @Nonnull
  public String getLastname() {
    return lastname;
  }

  public void setLastname(@Nonnull final String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(final String phone) {
    this.phone = phone;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(final String street) {
    this.street = street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }
}
