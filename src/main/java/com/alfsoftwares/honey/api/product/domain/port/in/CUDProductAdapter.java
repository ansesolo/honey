package com.alfsoftwares.honey.api.product.domain.port.in;

import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.model.RequestProduct;
import java.util.UUID;

public interface CUDProductAdapter {

  ProductEntity createProduct(RequestProduct product);

  ProductEntity updateProduct(UUID uuid, RequestProduct product);

  void deleteProduct(UUID uuid);
}
