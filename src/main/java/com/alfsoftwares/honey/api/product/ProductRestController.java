package com.alfsoftwares.honey.api.product;

import com.alfsoftwares.honey.api.product.application.model.ProductEntityModel;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.port.in.SearchProductAdapter;
import java.util.List;
import java.util.UUID;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@ExposesResourceFor(ProductEntity.class)
@RequestMapping(path = "/api/products")
public class ProductRestController implements ProductRestControllerDocumentation {

  private final SearchProductAdapter searchProductAdapter;

  public ProductRestController(final SearchProductAdapter searchProductAdapter) {
    this.searchProductAdapter = searchProductAdapter;
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<ProductEntityModel>> getProducts() {
    List<ProductEntity> products = searchProductAdapter.getAllProducts();

    return ResponseEntity.ok().body(products.stream().map(ProductEntityModel::new).toList());
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ProductEntityModel> getProduct(@PathVariable UUID uuid) {
    ProductEntity product = searchProductAdapter.getProduct(uuid);

    return ResponseEntity.ok().body(new ProductEntityModel(product));
  }
}
