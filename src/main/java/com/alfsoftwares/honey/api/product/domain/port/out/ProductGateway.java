package com.alfsoftwares.honey.api.product.domain.port.out;

import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {

  List<ProductEntity> findAll();

  Optional<ProductEntity> findByPublicId(UUID uuid);

  ProductEntity save(ProductEntity customer);

  void deleteById(Long id);
}
