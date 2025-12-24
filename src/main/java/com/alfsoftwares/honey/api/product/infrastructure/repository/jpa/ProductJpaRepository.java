package com.alfsoftwares.honey.api.product.infrastructure.repository.jpa;

import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByPublicId(UUID uuid);
}
