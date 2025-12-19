package com.alfsoftwares.honey.api.product.domain.port.in;

import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import java.util.List;

public interface SearchProductAdapter {

  List<ProductEntity> getAllProducts();

  ProductEntity getProduct(final Long id);
}
