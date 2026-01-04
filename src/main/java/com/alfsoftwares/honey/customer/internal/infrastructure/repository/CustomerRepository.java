package com.alfsoftwares.honey.customer.internal.infrastructure.repository;

import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import com.alfsoftwares.honey.customer.internal.domain.port.out.CustomerGateway;
import com.alfsoftwares.honey.customer.internal.infrastructure.repository.jpa.CustomerJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository implements CustomerGateway {

  private final CustomerJpaRepository jpa;

  public CustomerRepository(final CustomerJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public List<CustomerEntity> findAll() {
    Sort sort = Sort.by("lastname").ascending().and(Sort.by("firstname").ascending());

    return jpa.findAll(sort);
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
