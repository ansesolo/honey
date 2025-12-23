package com.alfsoftwares.honey.api.customer.domain.model;

import com.alfsoftwares.honey.api.core.domain.converter.LowerConverter;
import com.alfsoftwares.honey.api.core.domain.converter.PhoneConverter;
import com.alfsoftwares.honey.api.core.domain.converter.TitleCaseConverter;
import com.alfsoftwares.honey.api.core.domain.converter.UpperConverter;
import com.alfsoftwares.honey.api.core.domain.model.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

@Entity
public class CustomerEntity extends BaseEntity {

  @Convert(converter = TitleCaseConverter.class)
  private String firstname;

  @Convert(converter = UpperConverter.class)
  private String lastname;

  @Convert(converter = LowerConverter.class)
  private String email;

  @Convert(converter = PhoneConverter.class)
  private String phone;

  @Convert(converter = TitleCaseConverter.class)
  private String street;

  private String postalCode;

  @Convert(converter = UpperConverter.class)
  private String city;

  public CustomerEntity() {}

  private CustomerEntity(CustomerBuilder builder) {
    this.setId(builder.id);
    this.firstname = builder.firstname;
    this.lastname = builder.lastname;
    this.email = builder.email;
    this.phone = builder.phone;
    this.street = builder.street;
    this.postalCode = builder.postalCode;
    this.city = builder.city;
  }

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

  public static class CustomerBuilder {

    private final long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String street;
    private String postalCode;
    private String city;

    public CustomerBuilder(long id) {
      this.id = id;
    }

    public CustomerBuilder withIdentity(String firstname, String lastname) {
      this.firstname = firstname;
      this.lastname = lastname;
      return this;
    }

    public CustomerBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public CustomerBuilder withPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public CustomerBuilder withAddress(String street, String postalCode, String city) {
      this.street = street;
      this.postalCode = postalCode;
      this.city = city;
      return this;
    }

    public CustomerEntity build() {
      return new CustomerEntity(this);
    }
  }
}
