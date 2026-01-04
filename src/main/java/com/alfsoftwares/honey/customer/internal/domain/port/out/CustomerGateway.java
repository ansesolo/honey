package com.alfsoftwares.honey.customer.internal.domain.port.out;

import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerGateway {

  List<CustomerEntity> findAll();

  Optional<CustomerEntity> findByPublicId(final UUID uuid);

  CustomerEntity save(CustomerEntity customer);

  void deleteById(Long id);
}
