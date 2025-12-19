package com.alfsoftwares.honey.api.customer.domain.port.in;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import java.util.List;
import java.util.Optional;

public interface SearchCustomerAdapter {

  List<CustomerEntity> getAllCustomers();

  Optional<CustomerEntity> getCustomer(final Long id);
}
