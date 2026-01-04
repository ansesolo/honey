package com.alfsoftwares.honey.product.internal.domain.port.out;

import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {

  List<ProductEntity> findAll();

  Optional<ProductEntity> findByPublicId(UUID uuid);

  ProductEntity save(ProductEntity customer);

  void deleteById(Long id);
}
