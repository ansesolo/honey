package com.alfsoftwares.honey.product.internal.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.product.api.ProductDto;
import com.alfsoftwares.honey.product.internal.application.ProductRestController;
import org.springframework.hateoas.EntityModel;

public class ProductEntityModel extends EntityModel<ProductDto> {

  public ProductEntityModel(final ProductDto dto) {
    super(dto);
    add(linkTo(methodOn(ProductRestController.class).getProduct(dto.publicId())).withSelfRel());
    add(linkTo(methodOn(ProductRestController.class).getProducts()).withRel("allEntities"));
  }
}
