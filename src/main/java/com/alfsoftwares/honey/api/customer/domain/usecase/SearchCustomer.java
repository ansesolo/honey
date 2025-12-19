package com.alfsoftwares.honey.api.customer.domain.usecase;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.port.in.SearchCustomerAdapter;
import com.alfsoftwares.honey.api.customer.domain.port.out.CustomerGateway;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SearchCustomer implements SearchCustomerAdapter {

  private final CustomerGateway gateway;

  SearchCustomer(CustomerGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public List<CustomerEntity> getAllCustomers() {
    return gateway.findAll();
  }

  @Override
  public Optional<CustomerEntity> getCustomer(final Long id) {
    return gateway.findById(id);
  }
}
