package com.alfsoftwares.honey.api.product.domain.usecase;

import com.alfsoftwares.honey.api.core.application.error.NotFoundException;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.port.in.SearchProductAdapter;
import com.alfsoftwares.honey.api.product.domain.port.out.ProductGateway;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SearchProduct implements SearchProductAdapter {

  private final ProductGateway gateway;

  SearchProduct(ProductGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public List<ProductEntity> getAllProducts() {
    return gateway.findAll();
  }

  @Override
  public ProductEntity getProduct(final Long id) {
    Optional<ProductEntity> product = gateway.findById(id);
    if (product.isPresent()) {
      return product.get();
    }

    throw new NotFoundException("Product not found");
  }
}
