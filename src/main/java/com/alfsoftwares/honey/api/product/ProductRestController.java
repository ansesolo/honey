package com.alfsoftwares.honey.api.product;

import com.alfsoftwares.honey.api.core.domain.model.Unit;
import com.alfsoftwares.honey.api.product.application.model.EnumValue;
import com.alfsoftwares.honey.api.product.application.model.ProductEntityModel;
import com.alfsoftwares.honey.api.product.domain.model.ProductAttributes;
import com.alfsoftwares.honey.api.product.domain.model.ProductCategory;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import com.alfsoftwares.honey.api.product.domain.port.in.SearchProductAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@ExposesResourceFor(ProductEntity.class)
@RequestMapping(path = "/api/products")
public class ProductRestController implements ProductRestControllerDocumentation {

  private final SearchProductAdapter searchProductAdapter;

  public ProductRestController(final SearchProductAdapter searchProductAdapter) {
    this.searchProductAdapter = searchProductAdapter;
  }

  private final Map<String, Class<? extends Enum<?>>> allowedEnums =
      Map.of(
          "units", Unit.class,
          "attributes", ProductAttributes.class,
          "categories", ProductCategory.class);

  @GetMapping("/enum/{name}")
  public List<EnumValue> getEnumValues(@PathVariable String name) {
    Class<? extends Enum<?>> enumClass = allowedEnums.get(name.toLowerCase());

    if (enumClass == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enum non trouvé");
    }

    return Arrays.stream(enumClass.getEnumConstants())
        .map(e -> new EnumValue(e.name(), e.toString()))
        .toList();
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<ProductEntityModel>> getProducts() {
    List<ProductEntity> products = searchProductAdapter.getAllProducts();

    return ResponseEntity.ok().body(products.stream().map(ProductEntityModel::new).toList());
  }

  @GetMapping(path = "/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ProductEntityModel> getProduct(@PathVariable UUID uuid) {
    ProductEntity product = searchProductAdapter.getProduct(uuid);

    return ResponseEntity.ok().body(new ProductEntityModel(product));
  }
}
