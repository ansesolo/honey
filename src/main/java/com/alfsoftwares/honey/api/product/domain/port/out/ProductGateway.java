package com.alfsoftwares.honey.api.product.domain.port.out;

import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductGateway {

  List<ProductEntity> findAll();

  Optional<ProductEntity> findById(final Long id);
}
