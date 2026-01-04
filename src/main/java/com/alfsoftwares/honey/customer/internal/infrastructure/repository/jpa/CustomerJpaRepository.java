package com.alfsoftwares.honey.customer.internal.infrastructure.repository.jpa;

import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

  Optional<CustomerEntity> findByPublicId(UUID uuid);
}
