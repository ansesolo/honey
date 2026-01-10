package com.alfsoftwares.honey.product.internal.domain.usecase;

import com.alfsoftwares.honey.core.application.error.InvalidRequestException;
import com.alfsoftwares.honey.core.application.error.NotFoundException;
import com.alfsoftwares.honey.core.domain.model.Category;
import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import com.alfsoftwares.honey.product.internal.domain.model.RequestProduct;
import com.alfsoftwares.honey.product.internal.domain.port.in.CUDProductAdapter;
import com.alfsoftwares.honey.product.internal.domain.port.out.ProductGateway;
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
    validProduct(product);

    return gateway.save(product.toEntity());
  }

  @Override
  public ProductEntity updateProduct(UUID uuid, final RequestProduct product) {
    ProductEntity entity =
        gateway.findByPublicId(uuid).orElseThrow(() -> new NotFoundException("Product not found"));

    validProduct(product);

    return gateway.save(product.toEntity(entity));
  }

  private void validProduct(final RequestProduct product) {
    if (product.category().isNeedFlower() && product.flower() == null) {
      throw new InvalidRequestException("Flower is mandatory for this category");
    }

    if (Category.FULL_JAR == product.category() && product.weight() <= 0) {
      throw new InvalidRequestException("Weight is mandatory for this category");
    }
  }

  @Override
  public void deleteProduct(final UUID uuid) {
    ProductEntity entity =
        gateway.findByPublicId(uuid).orElseThrow(() -> new NotFoundException("Product not found"));

    gateway.deleteById(entity.getId());
  }
}
