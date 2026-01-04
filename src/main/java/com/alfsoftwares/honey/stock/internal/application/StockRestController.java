package com.alfsoftwares.honey.stock.internal.application;

import com.alfsoftwares.honey.stock.internal.application.model.StockEntityModel;
import com.alfsoftwares.honey.stock.internal.application.model.StockMovementRequest;
import com.alfsoftwares.honey.stock.internal.application.model.StockRule;
import com.alfsoftwares.honey.stock.internal.domain.model.MovementType;
import com.alfsoftwares.honey.stock.internal.domain.model.StockEntity;
import com.alfsoftwares.honey.stock.internal.domain.port.in.StockMovementAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@ExposesResourceFor(StockEntity.class)
@RequestMapping(path = "/api/stocks")
public class StockRestController implements StockRestControllerDocumentation {

  private final StockMovementAdapter stockMovementAdapter;
  private final StockMapper stockMapper;

  public StockRestController(
      final StockMovementAdapter stockMovementAdapter, final StockMapper stockMapper) {
    this.stockMovementAdapter = stockMovementAdapter;
    this.stockMapper = stockMapper;
  }

  @GetMapping("/rules")
  public ResponseEntity<List<StockRule>> getStockRules() {
    return ResponseEntity.ok()
        .body(Arrays.stream(MovementType.values()).map(StockRule::fromValue).toList());
  }

  @PostMapping(path = "/{movement}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> createStockMovement(@RequestBody StockMovementRequest stockMovement) {
    stockMovementAdapter.createStockMovement(stockMovement);

    return ResponseEntity.accepted().build();
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<StockEntityModel>> getStocks() {
    List<StockEntity> stocks = stockMovementAdapter.getStock();

    return ResponseEntity.ok()
        .body(
            stocks.stream().map(stockMapper::fromEntityToDto).map(StockEntityModel::new).toList());
  }

  @GetMapping(path = "/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<StockEntityModel> getStock(@PathVariable UUID uuid) {
    StockEntity stock = stockMovementAdapter.getStock(uuid);

    return ResponseEntity.ok().body(new StockEntityModel(stockMapper.fromEntityToDto(stock)));
  }
}
