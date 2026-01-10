package com.alfsoftwares.honey.stock.internal.domain.usecase;

import com.alfsoftwares.honey.core.application.error.InvalidRequestException;
import com.alfsoftwares.honey.core.application.error.NotFoundException;
import com.alfsoftwares.honey.product.api.ProductDto;
import com.alfsoftwares.honey.product.api.ProductServiceApi;
import com.alfsoftwares.honey.stock.internal.application.model.StockMovementRequest;
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
    if (stockMovement.quantity() == null
        || stockMovement.quantity() <= 0) {
      throw new InvalidRequestException("Quantity should be positive number");
    }
    ProductDto product = productAdapter.getProduct(stockMovement.productId());

    if (!stockMovement.type().getCategories().contains(product.category())) {
      throw new InvalidRequestException("Unauthorized stock movement for this product category");
    }

    if (product.category().isNeedFlower() && stockMovement.flower() == null) {
      throw new InvalidRequestException("Flower is needed for this stock movement");
    }

    Integer year = stockMovement.movementDate().getYear();

    StockEntity stockEntity =
        stockGateway
            .getStock(year, product.publicId(), stockMovement.flower())
            .orElseGet(StockEntity::new);

    if (stockMovement.type().getSign() < 0) {
      if (stockEntity.getQuantity() - stockMovement.quantity() < 0) {
        throw new InvalidRequestException("Insufficient stock");
      }
    }

    stockEntity.setStockYear(year);
    stockEntity.setProductId(product.publicId());
    stockEntity.setFlower(stockMovement.flower());
    Integer realValue = stockMovement.quantity() * stockMovement.type().getSign();
    stockEntity.setQuantity(stockEntity.getQuantity() + realValue);

    StockEntity savedEntity = stockGateway.save(stockEntity);

    StockMovementEntity stockMovementEntity = new StockMovementEntity();
    stockMovementEntity.setMovementDate(stockMovement.movementDate());
    stockMovementEntity.setStockId(savedEntity.getPublicId());
    stockMovementEntity.setMovementType(stockMovement.type());
    stockMovementEntity.setQuantity(realValue);
    stockMovementEntity.setPrice(stockMovement.price());
    stockMovementEntity.setReason(stockMovement.reason());

    stockGateway.save(stockMovementEntity);

    stockGateway.updateAveragePrice(savedEntity.getPublicId());
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
