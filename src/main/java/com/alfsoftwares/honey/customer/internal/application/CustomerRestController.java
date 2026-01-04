package com.alfsoftwares.honey.customer.internal.application;

import com.alfsoftwares.honey.customer.internal.application.dto.CustomerDto;
import com.alfsoftwares.honey.customer.internal.application.model.CustomerEntityModel;
import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import com.alfsoftwares.honey.customer.internal.domain.model.RequestCustomer;
import com.alfsoftwares.honey.customer.internal.domain.port.in.CUDCustomerAdapter;
import com.alfsoftwares.honey.customer.internal.domain.port.in.SearchCustomerAdapter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
    List<CustomerEntity> customers = searchCustomerAdapter.getAllCustomers();

    return ResponseEntity.ok()
        .body(
            customers.stream()
                .map(c -> new CustomerEntityModel(CustomerDto.fromEntity(c)))
                .toList());
  }

  @GetMapping(path = "/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> getCustomer(@PathVariable UUID uuid) {
    CustomerEntity customer = searchCustomerAdapter.getCustomer(uuid);

    return ResponseEntity.ok().body(new CustomerEntityModel(CustomerDto.fromEntity(customer)));
  }

  @PostMapping()
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> createCustomer(
      @Valid @RequestBody RequestCustomer customer) {
    CustomerEntity saved = cudCustomerAdapter.createCustomer(customer);

    CustomerEntityModel model = new CustomerEntityModel(CustomerDto.fromEntity(saved));

    return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
  }

  @PutMapping("/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> updateCustomer(
      @PathVariable UUID uuid, @Valid @RequestBody RequestCustomer customer) {
    CustomerEntity saved = cudCustomerAdapter.updateCustomer(uuid, customer);

    return ResponseEntity.ok().body(new CustomerEntityModel(CustomerDto.fromEntity(saved)));
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CustomerEntityModel> deleteCustomer(@PathVariable UUID uuid) {
    cudCustomerAdapter.deleteCustomer(uuid);

    return ResponseEntity.ok().build();
  }
}
