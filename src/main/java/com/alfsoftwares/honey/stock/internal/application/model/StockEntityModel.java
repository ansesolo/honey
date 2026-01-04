package com.alfsoftwares.honey.stock.internal.application.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.alfsoftwares.honey.stock.api.StockDto;
import com.alfsoftwares.honey.stock.internal.application.StockRestController;
import org.springframework.hateoas.EntityModel;

public class StockEntityModel extends EntityModel<StockDto> {

  public StockEntityModel(final StockDto stockDto) {
    super(stockDto);
    add(linkTo(methodOn(StockRestController.class).getStock(stockDto.publicId())).withSelfRel());
    add(linkTo(methodOn(StockRestController.class).getStocks()).withRel("allEntities"));
  }
}
