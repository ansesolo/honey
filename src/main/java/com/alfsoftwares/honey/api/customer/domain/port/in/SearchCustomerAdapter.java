package com.alfsoftwares.honey.api.customer.domain.port.in;

import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import java.util.List;

public interface SearchCustomerAdapter {

  List<CustomerEntity> getAllCustomers();

  CustomerEntity getCustomer(final Long id);
}
