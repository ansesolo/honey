package com.alfsoftwares.honey.product.internal.domain.port.in;

import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import com.alfsoftwares.honey.product.internal.domain.model.RequestProduct;
import java.util.UUID;

public interface CUDProductAdapter {

  ProductEntity createProduct(RequestProduct product);

  ProductEntity updateProduct(UUID uuid, RequestProduct product);

  void deleteProduct(UUID uuid);
}
