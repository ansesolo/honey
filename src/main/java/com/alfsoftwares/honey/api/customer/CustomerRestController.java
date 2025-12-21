package com.alfsoftwares.honey.api.customer;

import com.alfsoftwares.honey.api.customer.application.model.CustomerEntityModel;
import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.model.RequestCustomer;
import com.alfsoftwares.honey.api.customer.domain.port.in.CUDCustomerAdapter;
import com.alfsoftwares.honey.api.customer.domain.port.in.SearchCustomerAdapter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@ExposesResourceFor(CustomerEntity.class)
@RequestMapping(path = "/api/customers")
public class CustomerRestController implements CustomerRestControllerDocumentation {

  private final SearchCustomerAdapter searchCustomerAdapter;
  private final CUDCustomerAdapter cudCustomerAdapter;

  public CustomerRestController(
      final SearchCustomerAdapter searchCustomerAdapter,
      final CUDCustomerAdapter cudCustomerAdapter) {
    this.searchCustomerAdapter = searchCustomerAdapter;
    this.cudCustomerAdapter = cudCustomerAdapter;
  }

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<CustomerEntityModel>> getCustomers() {
    List<CustomerEntity> products = searchCustomerAdapter.getAllCustomers();

    return ResponseEntity.ok().body(products.stream().map(CustomerEntityModel::new).toList());
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> getCustomer(@PathVariable Long id) {
    CustomerEntity customer = searchCustomerAdapter.getCustomer(id);

    return ResponseEntity.ok().body(new CustomerEntityModel(customer));
  }

  @PostMapping()
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> createCustomer(
      @Valid @RequestBody RequestCustomer customer) {
    CustomerEntity saved = cudCustomerAdapter.createCustomer(customer);

    CustomerEntityModel model = new CustomerEntityModel(saved);

    return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> updateCustomer(
      @PathVariable Long id, @Valid @RequestBody RequestCustomer customer) {
    CustomerEntity saved = cudCustomerAdapter.updateCustomer(id, customer);

    return ResponseEntity.ok().body(new CustomerEntityModel(saved));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> deleteCustomer(@PathVariable Long id) {
    cudCustomerAdapter.deleteCustomer(id);

    return ResponseEntity.ok().build();
  }
}
