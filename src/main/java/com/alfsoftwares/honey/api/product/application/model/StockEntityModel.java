package com.alfsoftwares.honey.api.product.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.api.product.StockRestController;
import com.alfsoftwares.honey.api.product.domain.model.StockEntity;
import org.springframework.hateoas.EntityModel;

public class StockEntityModel extends EntityModel<StockEntity> {

  public StockEntityModel(final StockEntity entity) {
    super(entity);
    add(
        linkTo(methodOn(StockRestController.class).getStock(entity.getProduct().getId()))
            .withSelfRel());
    add(linkTo(methodOn(StockRestController.class).getStock()).withRel("allEntities"));
  }
}
