package com.alfsoftwares.honey.api.customer.domain.usecase;

import com.alfsoftwares.honey.api.core.application.error.NotFoundException;
import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.model.RequestCustomer;
import com.alfsoftwares.honey.api.customer.domain.port.in.CUDCustomerAdapter;
import com.alfsoftwares.honey.api.customer.domain.port.out.CustomerGateway;
import java.util.UUID;
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
  public CustomerEntity updateCustomer(UUID uuid, final RequestCustomer customer) {
    CustomerEntity entity =
        gateway.findByPublicId(uuid).orElseThrow(() -> new NotFoundException("Customer not found"));

    return gateway.save(customer.toEntity(entity));
  }

  @Override
  public void deleteCustomer(final UUID uuid) {
    CustomerEntity entity =
        gateway.findByPublicId(uuid).orElseThrow(() -> new NotFoundException("Customer not found"));

    gateway.deleteById(entity.getId());
  }
}
