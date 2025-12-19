package com.alfsoftwares.honey.api.product;

import com.alfsoftwares.honey.api.product.application.model.ProductEntityModel;
import com.alfsoftwares.honey.api.product.domain.model.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "Bearer Authentication")
public interface ProductRestControllerDocumentation {

  @Operation(
      summary = "Get all products",
      description = "Returns a list of product",
      tags = {"Products"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))),
        @ApiResponse(
            responseCode = "403",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The products was not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)))
      })
  ResponseEntity<List<ProductEntityModel>> getProducts();

  @Operation(
      summary = "Get a product",
      description = "Returns a particular product",
      tags = {"Products"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))),
        @ApiResponse(
            responseCode = "403",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  ResponseEntity<ProductEntityModel> getProduct(
      @Parameter(name = "id", description = "The product id", in = ParameterIn.PATH) Long id);
}
