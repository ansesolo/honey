package com.alfsoftwares.honey.customer.internal.domain.port.in;

import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import com.alfsoftwares.honey.customer.internal.domain.model.RequestCustomer;
import java.util.UUID;

public interface CUDCustomerAdapter {

  CustomerEntity createCustomer(RequestCustomer customer);

  CustomerEntity updateCustomer(UUID uuid, RequestCustomer customer);

  void deleteCustomer(UUID uuid);
}
