package com.alfsoftwares.honey.api.customer.domain.port.in;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.model.RequestCustomer;
import java.util.UUID;

public interface CUDCustomerAdapter {

  CustomerEntity createCustomer(RequestCustomer customer);

  CustomerEntity updateCustomer(UUID uuid, RequestCustomer customer);

  void deleteCustomer(UUID uuid);
}
