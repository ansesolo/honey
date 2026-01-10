package com.alfsoftwares.honey.product.internal.domain.model;

import com.alfsoftwares.honey.core.domain.model.Category;
import com.alfsoftwares.honey.core.domain.model.Flower;
import com.alfsoftwares.honey.core.domain.model.NamedEntity;
import com.alfsoftwares.honey.core.domain.model.Unit;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class ProductEntity extends NamedEntity {

  @Enumerated(EnumType.STRING)
  private Unit unit;

  private BigDecimal defaultPrice;

  @Enumerated(EnumType.STRING)
  private Category category;

  @Enumerated(EnumType.STRING)
  private Flower flower;

  private Integer weight = 0;

  public ProductEntity() {}

  private ProductEntity(ProductBuilder builder) {
    this.setId(builder.id);
    this.setName(builder.name);
    this.unit = builder.unit;
    this.defaultPrice = builder.defaultPrice;
    this.category = builder.category;
    this.flower = builder.flower;
    this.weight = builder.weight;
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

  public Flower getFlower() {
    return flower;
  }

  public void setFlower(final Flower flower) {
    this.flower = flower;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(final Integer weight) {
    this.weight = weight;
  }

  public static class ProductBuilder {

    private final long id;
    private final String name;
    private Unit unit = Unit.UNIT;
    private BigDecimal defaultPrice = BigDecimal.valueOf(0L);
    private Category category;
    private Flower flower;
    private Integer weight;

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

    public ProductBuilder withWeight(Integer weight) {
      this.weight = weight;
      return this;
    }

    public ProductBuilder withFlower(Flower flower) {
      this.flower = flower;
      return this;
    }

    public ProductEntity build() {
      return new ProductEntity(this);
    }
  }
}
