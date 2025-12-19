package com.alfsoftwares.honey.api.customer;

import com.alfsoftwares.honey.api.customer.application.model.CustomerEntityModel;
import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.port.in.SearchCustomerAdapter;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@ExposesResourceFor(CustomerEntity.class)
@RequestMapping(path = "/api/customers")
public class CustomerRestController implements CustomerRestControllerDocumentation {

  private final SearchCustomerAdapter searchCustomerAdapter;

  public CustomerRestController(final SearchCustomerAdapter searchCustomerAdapter) {
    this.searchCustomerAdapter = searchCustomerAdapter;
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
    Optional<CustomerEntity> customer = searchCustomerAdapter.getCustomer(id);

    return customer
        .map(entity -> ResponseEntity.ok().body(new CustomerEntityModel(entity)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
