package com.alfsoftwares.honey.product.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.Category;
import com.alfsoftwares.honey.core.domain.model.NamedEntity;
import com.alfsoftwares.honey.core.domain.model.Unit;
import com.alfsoftwares.honey.product.internal.infrastructure.repository.converter.JsonMapConverter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "product")
public class ProductEntity extends NamedEntity {

  @Enumerated(EnumType.STRING)
  private Unit unit;

  private BigDecimal defaultPrice;

  @Enumerated(EnumType.STRING)
  private Category category;

  @Convert(converter = JsonMapConverter.class)
  @Column(name = "attributes_json")
  private Map<ProductAttributes, Object> attributes;

  public ProductEntity() {}

  private ProductEntity(ProductBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.unit = builder.unit;
    this.defaultPrice = builder.defaultPrice;
    this.category = builder.category;
    this.attributes = builder.attributes;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(final Unit unit) {
    this.unit = unit;
  }

  public BigDecimal getDefaultPrice() {
    return defaultPrice;
  }

  public void setDefaultPrice(final BigDecimal defaultPrice) {
    this.defaultPrice = defaultPrice;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(final Category category) {
    this.category = category;
  }

  public Map<ProductAttributes, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(final Map<ProductAttributes, Object> attributes) {
    this.attributes = attributes;
  }

  public static class ProductBuilder {

    private final long id;
    private final String name;
    private Unit unit = Unit.UNIT;
    private BigDecimal defaultPrice = BigDecimal.valueOf(0L);
    private Category category;
    private Map<ProductAttributes, Object> attributes = new HashMap<>();

    public ProductBuilder(long id, String name) {
      this.id = id;
      this.name = name;
    }

    public ProductBuilder withUnit(Unit unit) {
      this.unit = unit;
      return this;
    }

    public ProductBuilder withPrice(BigDecimal defaultPrice) {
      this.defaultPrice = defaultPrice;
      return this;
    }

    public ProductBuilder withCategory(Category category) {
      this.category = category;
      return this;
    }

    public ProductBuilder withAttributes(Map<ProductAttributes, Object> attributes) {
      this.attributes = attributes;
      return this;
    }

    public ProductEntity build() {
      return new ProductEntity(this);
    }
  }
}
