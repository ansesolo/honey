package com.alfsoftwares.honey.api.product.domain.usecase;

import com.alfsoftwares.honey.api.core.application.error.NotFoundException;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.model.RequestProduct;
import com.alfsoftwares.honey.api.product.domain.port.in.CUDProductAdapter;
import com.alfsoftwares.honey.api.product.domain.port.out.ProductGateway;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CUDProduct implements CUDProductAdapter {

  private final ProductGateway gateway;

  public CUDProduct(final ProductGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public ProductEntity createProduct(final RequestProduct product) {
    return gateway.save(product.toEntity());
  }

  @Override
  public ProductEntity updateProduct(UUID uuid, final RequestProduct product) {
    ProductEntity entity =
        gateway.findByPublicId(uuid).orElseThrow(() -> new NotFoundException("Product not found"));

    return gateway.save(product.toEntity(entity));
  }

  @Override
  public void deleteProduct(final UUID uuid) {
    ProductEntity entity =
        gateway.findByPublicId(uuid).orElseThrow(() -> new NotFoundException("Product not found"));

    gateway.deleteById(entity.getId());
  }
}
