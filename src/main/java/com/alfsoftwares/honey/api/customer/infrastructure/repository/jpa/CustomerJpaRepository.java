package com.alfsoftwares.honey.api.customer.infrastructure.repository.jpa;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {}
