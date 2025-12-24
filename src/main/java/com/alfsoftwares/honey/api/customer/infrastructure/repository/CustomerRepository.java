package com.alfsoftwares.honey.api.customer.infrastructure.repository;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.port.out.CustomerGateway;
import com.alfsoftwares.honey.api.customer.infrastructure.repository.jpa.CustomerJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository implements CustomerGateway {

  CustomerJpaRepository jpa;

  public CustomerRepository(final CustomerJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public List<CustomerEntity> findAll() {
    return jpa.findAll();
  }

  @Override
  public Optional<CustomerEntity> findByPublicId(final UUID uuid) {
    return jpa.findByPublicId(uuid);
  }

  @Override
  public CustomerEntity save(CustomerEntity customer) {
    return jpa.save(customer);
  }

  @Override
  public void deleteById(Long id) {
    jpa.deleteById(id);
  }
}
