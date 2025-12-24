package com.alfsoftwares.honey.api.product.domain.usecase;

import com.alfsoftwares.honey.api.core.application.error.InvalidRequestException;
import com.alfsoftwares.honey.api.core.application.error.NotFoundException;
import com.alfsoftwares.honey.api.product.application.model.StockMovementRequest;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.model.StockEntity;
import com.alfsoftwares.honey.api.product.domain.model.StockMovementEntity;
import com.alfsoftwares.honey.api.product.domain.port.in.StockMovementAdapter;
import com.alfsoftwares.honey.api.product.domain.port.out.ProductGateway;
import com.alfsoftwares.honey.api.product.domain.port.out.StockGateway;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StockMovement implements StockMovementAdapter {

  private final StockGateway stockGateway;
  private final ProductGateway productGateway;

  StockMovement(StockGateway stockGateway, final ProductGateway productGateway) {
    this.stockGateway = stockGateway;
    this.productGateway = productGateway;
  }

  @Override
  public void createStockMovement(final StockMovementRequest stockMovement) {
    if (stockMovement.quantity().longValue() < 0) {
      throw new InvalidRequestException("Quantity should be positive number");
    }
    Optional<ProductEntity> product = productGateway.findByPublicId(stockMovement.productId());
    if (product.isEmpty()) {
      throw new NotFoundException("Unknown product");
    }
    if (stockMovement.type().getSign() < 0) {
      Optional<StockEntity> stock = stockGateway.getStock(product.get().getPublicId());
      if (stock.isEmpty()
          || stock.get().getQuantity().subtract(stockMovement.quantity()).toBigInteger().longValue()
              < 0) {
        throw new InvalidRequestException("Insufficient stock");
      }
    }
    if (!stockMovement.type().getCategories().contains(product.get().getCategory())) {
      throw new InvalidRequestException("Unauthorized stock movement for this product category");
    }

    StockMovementEntity stockMovementEntity = new StockMovementEntity();
    stockMovementEntity.setProduct(product.get());
    stockMovementEntity.setType(stockMovement.type());
    stockMovementEntity.setQuantity(stockMovement.quantity());
    stockMovementEntity.setReason(stockMovement.reason());

    stockGateway.save(stockMovementEntity);
  }

  @Override
  public List<StockEntity> getStock() {
    return stockGateway.getStock();
  }

  @Override
  public StockEntity getStock(UUID uuid) {
    Optional<ProductEntity> product = productGateway.findByPublicId(uuid);
    if (product.isPresent()) {
      Optional<StockEntity> stock = stockGateway.getStock(product.get().getPublicId());
      if (stock.isPresent()) {
        return stock.get();
      }
      throw new NotFoundException("Stock not found");
    }
    throw new NotFoundException("Product not found");
  }
}
