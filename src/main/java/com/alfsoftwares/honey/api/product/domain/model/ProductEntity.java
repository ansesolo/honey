package com.alfsoftwares.honey.api.product.domain.model;

import com.alfsoftwares.honey.api.core.domain.model.NamedEntity;
import com.alfsoftwares.honey.api.core.domain.model.Unit;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class ProductEntity extends NamedEntity {

  private Unit unit;
  private BigDecimal defaultPrice;
  private String unicity;
  private ProductCategory category;
  private Map<ProductAttributes, Object> attributes;

  private ProductEntity(ProductBuilder builder) {
    this.setId(builder.id);
    this.setCreatedAt(ZonedDateTime.now());
    this.setName(builder.name);
    this.unit = builder.unit;
    this.defaultPrice = builder.defaultPrice;
    this.unicity = builder.unicity;
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

  public String getUnicity() {
    return unicity;
  }

  public void setUnicity(final String unicity) {
    this.unicity = unicity;
  }

  public ProductCategory getCategory() {
    return category;
  }

  public void setCategory(final ProductCategory category) {
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
    private String unicity;
    private ProductCategory category;
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

    public ProductBuilder withUnicity(String unicity) {
      this.unicity = unicity;
      return this;
    }

    public ProductBuilder withCategory(ProductCategory category) {
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
