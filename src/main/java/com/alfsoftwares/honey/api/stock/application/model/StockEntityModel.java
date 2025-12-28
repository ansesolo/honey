package com.alfsoftwares.honey.api.stock.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.api.stock.StockRestController;
import com.alfsoftwares.honey.api.stock.domain.model.StockEntity;
import org.springframework.hateoas.EntityModel;

public class StockEntityModel extends EntityModel<StockEntity> {

  public StockEntityModel(final StockEntity entity) {
    super(entity);
    add(
        linkTo(methodOn(StockRestController.class).getStock(entity.getProduct().getPublicId()))
            .withSelfRel());
    add(linkTo(methodOn(StockRestController.class).getStocks()).withRel("allEntities"));
  }
}
