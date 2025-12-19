package com.alfsoftwares.honey.api.customer.infrastructure.repository;

import static com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity.CustomerBuilder;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.port.out.CustomerGateway;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository implements CustomerGateway {

  private final Map<Long, CustomerEntity> customers = new HashMap<>();

  public CustomerRepository() {
    customers.put(
        1L,
        new CustomerBuilder(1L)
            .withIdentity("fabien", "allemand")
            .withEmail("test@email.com")
            .withPhone("0625445898")
            .withAddress("20 rue claire", "69400", "anse")
            .build());
    customers.put(
        2L,
        new CustomerBuilder(2L)
            .withIdentity("Laurent", "marcon")
            .withEmail("toto@yahoo.fr")
            .build());
    customers.put(
        3L,
        new CustomerBuilder(3L).withIdentity("Serge", "moulin").withPhone("0632558965").build());
    customers.put(
        4L,
        new CustomerBuilder(4L)
            .withIdentity("France", "saez")
            .withAddress("12 rue lorin", "43370", "cussac sur loire")
            .build());
    customers.put(
        5L,
        new CustomerBuilder(5L)
            .withIdentity("Jean", "peuplus")
            .withEmail("azertyuiop@outlook.fr")
            .withPhone("0632558895")
            .build());
    customers.put(6L, new CustomerBuilder(6L).build());
    customers.put(7L, new CustomerBuilder(7L).withIdentity("alain", "terieur").build());
    customers.put(8L, new CustomerBuilder(8L).withIdentity("alain1", "terieur").build());
    customers.put(9L, new CustomerBuilder(9L).withIdentity("alain2", "terieur").build());
    customers.put(10L, new CustomerBuilder(10L).withIdentity("alain3", "terieur").build());
    customers.put(11L, new CustomerBuilder(11L).withIdentity("alain4", "terieur").build());
    customers.put(12L, new CustomerBuilder(12L).withIdentity("alain5", "terieur").build());
    customers.put(13L, new CustomerBuilder(13L).withIdentity("alain6", "terieur").build());
    customers.put(14L, new CustomerBuilder(14L).withIdentity("alain7", "terieur").build());
    customers.put(15L, new CustomerBuilder(15L).withIdentity("alain8", "terieur").build());
    customers.put(16L, new CustomerBuilder(16L).withIdentity("alain9", "terieur").build());
    customers.put(17L, new CustomerBuilder(17L).withIdentity("alain10", "terieur").build());
    customers.put(18L, new CustomerBuilder(18L).withIdentity("alain11", "terieur").build());
    customers.put(19L, new CustomerBuilder(19L).withIdentity("alain12", "terieur").build());
    customers.put(20L, new CustomerBuilder(20L).withIdentity("alain13", "terieur").build());
    customers.put(21L, new CustomerBuilder(21L).withIdentity("alain14", "terieur").build());
    customers.put(22L, new CustomerBuilder(22L).withIdentity("alain15", "terieur").build());
    customers.put(23L, new CustomerBuilder(23L).withIdentity("alain16", "terieur").build());
    customers.put(24L, new CustomerBuilder(24L).withIdentity("alain17", "terieur").build());
    customers.put(25L, new CustomerBuilder(25L).withIdentity("alain18", "terieur").build());
    customers.put(26L, new CustomerBuilder(26L).withIdentity("alain19", "terieur").build());
    customers.put(27L, new CustomerBuilder(27L).withIdentity("alain20", "terieur").build());
    customers.put(28L, new CustomerBuilder(28L).withIdentity("alain21", "terieur").build());
    customers.put(29L, new CustomerBuilder(29L).withIdentity("alain22", "terieur").build());
    customers.put(30L, new CustomerBuilder(30L).withIdentity("alain23", "terieur").build());
    customers.put(31L, new CustomerBuilder(31L).withIdentity("alain24", "terieur").build());
    customers.put(32L, new CustomerBuilder(32L).withIdentity("alain25", "terieur").build());
  }

  @Override
  public List<CustomerEntity> findAll() {
    return customers.values().stream().toList();
  }

  @Override
  public Optional<CustomerEntity> findById(final Long id) {
    return Optional.ofNullable(customers.get(id));
  }
}
