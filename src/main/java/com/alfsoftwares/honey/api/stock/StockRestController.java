package com.alfsoftwares.honey.api.stock;

import com.alfsoftwares.honey.api.stock.application.model.StockEntityModel;
import com.alfsoftwares.honey.api.stock.application.model.StockMovementRequest;
import com.alfsoftwares.honey.api.stock.domain.model.StockEntity;
import com.alfsoftwares.honey.api.stock.domain.port.in.StockMovementAdapter;
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

  public StockRestController(final StockMovementAdapter stockMovementAdapter) {
    this.stockMovementAdapter = stockMovementAdapter;
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> createStockMovement(@RequestBody StockMovementRequest stockMovement) {
    stockMovementAdapter.createStockMovement(stockMovement);

    return ResponseEntity.accepted().build();
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<StockEntityModel>> getStocks() {
    List<StockEntity> stocks = stockMovementAdapter.getStock();

    return ResponseEntity.ok().body(stocks.stream().map(StockEntityModel::new).toList());
  }

  @GetMapping(path = "/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<StockEntityModel> getStock(@PathVariable UUID uuid) {
    StockEntity stock = stockMovementAdapter.getStock(uuid);

    return ResponseEntity.ok().body(new StockEntityModel(stock));
  }
}
