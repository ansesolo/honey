package com.alfsoftwares.honey.product.internal.application;

import static com.alfsoftwares.honey.product.internal.application.ProductMapper.fromEntityToDto;

import com.alfsoftwares.honey.product.api.ProductDto;
import com.alfsoftwares.honey.product.api.ProductServiceApi;
import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import com.alfsoftwares.honey.product.internal.domain.port.in.SearchProductAdapter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService implements ProductServiceApi {

  private final SearchProductAdapter searchProductAdapter;

  public ProductService(final SearchProductAdapter searchProductAdapter) {
    this.searchProductAdapter = searchProductAdapter;
  }

  @Override
  public ProductDto getProduct(final UUID uuid) {
    ProductEntity product = searchProductAdapter.getProduct(uuid);

    return fromEntityToDto(product);
  }
}
