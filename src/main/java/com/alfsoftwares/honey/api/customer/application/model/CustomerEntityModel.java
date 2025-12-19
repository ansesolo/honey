package com.alfsoftwares.honey.api.customer.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.api.customer.CustomerRestController;
import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
import org.springframework.hateoas.EntityModel;

public class CustomerEntityModel extends EntityModel<CustomerEntity> {

  public CustomerEntityModel(final CustomerEntity entity) {
    super(entity);
    add(linkTo(methodOn(CustomerRestController.class).getCustomer(entity.getId())).withSelfRel());
    add(linkTo(methodOn(CustomerRestController.class).getCustomers()).withRel("allEntities"));
  }
}
