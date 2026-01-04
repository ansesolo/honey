package com.alfsoftwares.honey.product.internal.infrastructure.repository;

import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import com.alfsoftwares.honey.product.internal.domain.port.out.ProductGateway;
import com.alfsoftwares.honey.product.internal.infrastructure.repository.jpa.ProductJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository implements ProductGateway {

  private final ProductJpaRepository jpa;

  public ProductRepository(final ProductJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public List<ProductEntity> findAll() {
    Sort sort = Sort.by("name").ascending();

    return jpa.findAll(sort);
  }

  @Override
  public Optional<ProductEntity> findByPublicId(final UUID uuid) {
    return jpa.findByPublicId(uuid);
  }

  @Override
  public ProductEntity save(ProductEntity product) {
    return jpa.save(product);
  }

  @Override
  public void deleteById(Long id) {
    jpa.deleteById(id);
  }
}
