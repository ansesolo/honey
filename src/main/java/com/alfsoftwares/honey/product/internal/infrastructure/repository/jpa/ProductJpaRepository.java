package com.alfsoftwares.honey.product.internal.infrastructure.repository.jpa;

import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByPublicId(UUID uuid);
}
