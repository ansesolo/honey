package com.alfsoftwares.honey.api.customer.domain.usecase;

import com.alfsoftwares.honey.api.core.application.error.NotFoundException;
import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.model.RequestCustomer;
import com.alfsoftwares.honey.api.customer.domain.port.in.CUDCustomerAdapter;
import com.alfsoftwares.honey.api.customer.domain.port.out.CustomerGateway;
import org.springframework.stereotype.Service;

@Service
public class CUDCustomer implements CUDCustomerAdapter {

  private final CustomerGateway gateway;

  public CUDCustomer(final CustomerGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public CustomerEntity createCustomer(final RequestCustomer customer) {
    return gateway.save(customer.toEntity());
  }

  @Override
  public CustomerEntity updateCustomer(long id, final RequestCustomer customer) {
    gateway.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));

    return gateway.save(customer.toEntity(id));
  }

  @Override
  public void deleteCustomer(final Long id) {
    gateway.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));

    gateway.deleteById(id);
  }
}
