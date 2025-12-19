package com.alfsoftwares.honey.api.product.infrastructure.repository;

import static com.alfsoftwares.honey.api.product.domain.model.ProductEntity.ProductBuilder;

import com.alfsoftwares.honey.api.core.domain.model.Unit;
import com.alfsoftwares.honey.api.product.domain.model.ProductAttributes;
import com.alfsoftwares.honey.api.product.domain.model.ProductCategory;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.port.out.ProductGateway;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository implements ProductGateway {

  private final Map<Long, ProductEntity> products = new HashMap<>();

  public ProductRepository() {
    products.put(
        1L,
        new ProductBuilder(1L, "POT VIDE 1KG")
            .withPrice(BigDecimal.valueOf(100L))
            .withUnit(Unit.UNIT)
            .withCategory(ProductCategory.EMPTY_JAR)
            .withUnicity("EJ1KG")
            .withAttributes(Map.of(ProductAttributes.SIZE, "1KG"))
            .build());
    products.put(
        2L,
        new ProductBuilder(2L, "POT VIDE 500g")
            .withPrice(BigDecimal.valueOf(50))
            .withUnit(Unit.UNIT)
            .withCategory(ProductCategory.EMPTY_JAR)
            .withUnicity("EJ500G")
            .withAttributes(Map.of(ProductAttributes.SIZE, "500G"))
            .build());
    products.put(
        3L,
        new ProductBuilder(3L, "Barquette")
            .withPrice(BigDecimal.valueOf(50))
            .withUnit(Unit.UNIT)
            .withCategory(ProductCategory.EMPTY_TRAY)
            .withUnicity("ET")
            .build());
    products.put(
        4L,
        new ProductBuilder(4L, "POT MIEL 1KG")
            .withPrice(BigDecimal.valueOf(1300))
            .withUnit(Unit.UNIT)
            .withCategory(ProductCategory.FULL_JAR)
            .withUnicity("FJ1KG")
            .withAttributes(
                Map.of(ProductAttributes.SIZE, "1KG", ProductAttributes.FLOWER, "Acacia"))
            .build());
    products.put(
        7L,
        new ProductBuilder(7L, "MIEL")
            .withUnit(Unit.GRAM)
            .withCategory(ProductCategory.BULK_HONEY)
            .withUnicity("BKH")
            .withAttributes(
                Map.of(ProductAttributes.WEIGHT, "1520G", ProductAttributes.FLOWER, "Acacia"))
            .build());
  }

  @Override
  public List<ProductEntity> findAll() {
    return products.values().stream().toList();
  }

  @Override
  public Optional<ProductEntity> findById(final Long id) {
    return Optional.ofNullable(products.get(id));
  }
}
