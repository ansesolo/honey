package com.alfsoftwares.honey.stock.internal.domain.usecase;

import com.alfsoftwares.honey.core.application.error.InvalidRequestException;
import com.alfsoftwares.honey.core.application.error.NotFoundException;
import com.alfsoftwares.honey.product.api.ProductDto;
import com.alfsoftwares.honey.product.api.ProductServiceApi;
import com.alfsoftwares.honey.stock.internal.application.model.StockMovementRequest;
import com.alfsoftwares.honey.stock.internal.domain.model.MovementType;
import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import com.alfsoftwares.honey.stock.internal.domain.model.StockMovementEntity;
import com.alfsoftwares.honey.stock.internal.domain.port.in.StockMovementAdapter;
import com.alfsoftwares.honey.stock.internal.domain.port.out.StockGateway;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StockMovement implements StockMovementAdapter {

  private final StockGateway stockGateway;
  private final ProductServiceApi productAdapter;

  StockMovement(StockGateway stockGateway, final ProductServiceApi productAdapter) {
    this.stockGateway = stockGateway;
    this.productAdapter = productAdapter;
  }

  @Override
  @Transactional
  public void createStockMovement(final StockMovementRequest stockMovement) {
    ProductDto product = productAdapter.getProduct(stockMovement.productId());

    Integer year = stockMovement.movementDate().getYear();

    StockEntity stockEntity =
        stockGateway.getStock(year, product.publicId()).orElseGet(StockEntity::new);

    validStockMovement(stockMovement, product, stockEntity);

    Integer realValue = stockMovement.quantity() * stockMovement.type().getSign();

    StockEntity savedEntity = updateStock(stockMovement, stockEntity, year, product, realValue);
    createStockMovement(stockMovement, savedEntity, realValue);
    stockGateway.updateAveragePrice(savedEntity.getPublicId());

    // Manage stock of linked product
    if (MovementType.PRODUCTION == stockMovement.type()) {}
  }

  private void createStockMovement(
      final StockMovementRequest stockMovement,
      final StockEntity savedEntity,
      final Integer realValue) {
    StockMovementEntity stockMovementEntity = new StockMovementEntity();
    stockMovementEntity.setMovementDate(stockMovement.movementDate());
    stockMovementEntity.setStockId(savedEntity.getPublicId());
    stockMovementEntity.setMovementType(stockMovement.type());
    stockMovementEntity.setQuantity(realValue);
    stockMovementEntity.setPrice(stockMovement.price());
    stockMovementEntity.setReason(stockMovement.reason());

    stockGateway.save(stockMovementEntity);
  }

  private StockEntity updateStock(
      final StockMovementRequest stockMovement,
      final StockEntity stockEntity,
      final Integer year,
      final ProductDto product,
      final Integer realValue) {
    stockEntity.setStockYear(year);
    stockEntity.setProductId(product.publicId());
    stockEntity.setQuantity(stockEntity.getQuantity() + realValue);

    return stockGateway.save(stockEntity);
  }

  private void validStockMovement(
      final StockMovementRequest stockMovement, ProductDto product, StockEntity stockEntity) {
    if (stockMovement.quantity() == null || stockMovement.quantity() <= 0) {
      throw new InvalidRequestException("Quantity should be positive number");
    }

    if (!stockMovement.type().getCategories().contains(product.category())) {
      throw new InvalidRequestException("Unauthorized stock movement for this product category");
    }

    if (stockMovement.type().getSign() < 0) {
      if (stockEntity.getQuantity() - stockMovement.quantity() < 0) {
        throw new InvalidRequestException("Insufficient stock");
      }
    }
  }

  @Override
  public List<StockEntity> getStock() {
    return stockGateway.getStock();
  }

  @Override
  public StockEntity getStock(UUID uuid) {
    Optional<StockEntity> stock = stockGateway.getStock(uuid);
    if (stock.isPresent()) {
      return stock.get();
    }
    throw new NotFoundException("Stock not found");
  }
}
