package com.alfsoftwares.honey.customer.internal.domain.port.in;

import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import java.util.List;
import java.util.UUID;

public interface SearchCustomerAdapter {

  List<CustomerEntity> getAllCustomers();

  CustomerEntity getCustomer(final UUID uuid);
}
