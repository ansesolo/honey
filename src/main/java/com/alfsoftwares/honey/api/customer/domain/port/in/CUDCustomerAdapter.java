package com.alfsoftwares.honey.api.customer.domain.port.in;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import com.alfsoftwares.honey.api.customer.domain.model.RequestCustomer;

public interface CUDCustomerAdapter {

  CustomerEntity createCustomer(RequestCustomer customer);

  CustomerEntity updateCustomer(long id, RequestCustomer customer);

  void deleteCustomer(Long id);
}
