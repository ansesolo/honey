package com.alfsoftwares.honey.product.api;

import java.util.UUID;

public interface ProductServiceApi {
  ProductDto getProduct(final UUID uuid);
}
