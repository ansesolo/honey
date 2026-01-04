package com.alfsoftwares.honey.customer.internal.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.customer.internal.application.CustomerRestController;
import com.alfsoftwares.honey.customer.internal.application.dto.CustomerDto;
import org.springframework.hateoas.EntityModel;

public class CustomerEntityModel extends EntityModel<CustomerDto> {

  public CustomerEntityModel(final CustomerDto dto) {
    super(dto);
    add(linkTo(methodOn(CustomerRestController.class).getCustomer(dto.publicId())).withSelfRel());
    add(linkTo(methodOn(CustomerRestController.class).getCustomers()).withRel("allEntities"));
  }
}
