package com.alfsoftwares.honey.product.internal.domain.port.in;

import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import java.util.List;
import java.util.UUID;

public interface SearchProductAdapter {

  List<ProductEntity> getAllProducts();

  ProductEntity getProduct(final UUID uuid);
}
