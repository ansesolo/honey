package com.alfsoftwares.honey.api.product.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.api.product.ProductRestController;
import com.alfsoftwares.honey.api.product.application.dto.ProductDto;
import org.springframework.hateoas.EntityModel;

public class ProductEntityModel extends EntityModel<ProductDto> {

  public ProductEntityModel(final ProductDto dto) {
    super(dto);
    add(linkTo(methodOn(ProductRestController.class).getProduct(dto.publicId())).withSelfRel());
    add(linkTo(methodOn(ProductRestController.class).getProducts()).withRel("allEntities"));
  }
}
