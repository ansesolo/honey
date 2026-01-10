package com.alfsoftwares.honey.product.internal.application;

import static com.alfsoftwares.honey.product.internal.application.ProductMapper.fromEntityToDto;

import com.alfsoftwares.honey.core.domain.model.Category;
import com.alfsoftwares.honey.core.domain.model.Flower;
import com.alfsoftwares.honey.core.domain.model.Unit;
import com.alfsoftwares.honey.product.api.ProductServiceApi;
import com.alfsoftwares.honey.product.internal.application.model.EnumValue;
import com.alfsoftwares.honey.product.internal.application.model.ProductEntityModel;
import com.alfsoftwares.honey.product.internal.domain.model.ProductAttributes;
import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import com.alfsoftwares.honey.product.internal.domain.model.RequestProduct;
import com.alfsoftwares.honey.product.internal.domain.port.in.CUDProductAdapter;
import com.alfsoftwares.honey.product.internal.domain.port.in.SearchProductAdapter;
import jakarta.validation.Valid;
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

  private final ProductServiceApi productApi;
  private final SearchProductAdapter searchProductAdapter;
  private final CUDProductAdapter cudProductAdapter;
  private final Map<String, Class<? extends Enum<?>>> allowedEnums =
      Map.of(
          "units", Unit.class,
          "attributes", ProductAttributes.class,
          "flowers", Flower.class,
          "categories", Category.class);

  public ProductRestController(
      final ProductServiceApi productApi,
      final SearchProductAdapter searchProductAdapter,
      final CUDProductAdapter cudProductAdapter) {
    this.productApi = productApi;
    this.searchProductAdapter = searchProductAdapter;
    this.cudProductAdapter = cudProductAdapter;
  }

  @GetMapping("/enum/{name}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<EnumValue>> getEnumValues(@PathVariable String name) {
    Class<? extends Enum<?>> enumClass = allowedEnums.get(name.toLowerCase());

    if (enumClass == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enum non trouvé");
    }

    return ResponseEntity.ok()
        .body(
            Arrays.stream(enumClass.getEnumConstants())
                .map(e -> new EnumValue(e.name(), e.toString()))
                .toList());
  }

  @GetMapping("/categories/hasflowers")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<EnumValue>> getCategoriesWithFlower() {

    return ResponseEntity.ok()
        .body(
            Arrays.stream(Category.values())
                .filter(Category::isNeedFlower)
                .map(c -> new EnumValue(c.name(), c.toString()))
                .toList());
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<ProductEntityModel>> getProducts() {
    List<ProductEntity> products = searchProductAdapter.getAllProducts();

    return ResponseEntity.ok()
        .body(products.stream().map(p -> new ProductEntityModel(fromEntityToDto(p))).toList());
  }

  @GetMapping(path = "/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ProductEntityModel> getProduct(@PathVariable UUID uuid) {
    return ResponseEntity.ok().body(new ProductEntityModel(productApi.getProduct(uuid)));
  }

  @PostMapping()
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ProductEntityModel> createProduct(
      @Valid @RequestBody RequestProduct product) {
    ProductEntity saved = cudProductAdapter.createProduct(product);

    ProductEntityModel model = new ProductEntityModel(fromEntityToDto(saved));

    return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
  }

  @PutMapping("/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ProductEntityModel> updateProduct(
      @PathVariable UUID uuid, @Valid @RequestBody RequestProduct product) {
    ProductEntity saved = cudProductAdapter.updateProduct(uuid, product);

    return ResponseEntity.ok().body(new ProductEntityModel(fromEntityToDto(saved)));
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ProductEntityModel> deleteProduct(@PathVariable UUID uuid) {
    cudProductAdapter.deleteProduct(uuid);

    return ResponseEntity.ok().build();
  }
}
