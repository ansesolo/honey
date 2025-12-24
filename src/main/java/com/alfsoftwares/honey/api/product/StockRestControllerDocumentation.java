package com.alfsoftwares.honey.api.product;

import com.alfsoftwares.honey.api.product.application.model.StockEntityModel;
import com.alfsoftwares.honey.api.product.application.model.StockMovementRequest;
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
import java.util.UUID;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "Bearer Authentication")
public interface StockRestControllerDocumentation {

  @Operation(
      summary = "Stock movement",
      description = "Create a stock movement",
      tags = {"Stock"})
  @ApiResponses(
      value = {
        @ApiResponse(
                responseCode = "202",
                description = "Request accepted",
                content =
                    @Content(
                        mediaType = "application/json",
                        array =
                            @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content =
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
                responseCode = "403",
                description = "Not authorized",
                content =
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Void.class))),
            @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content =
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProblemDetail.class)))
      })
  ResponseEntity<Void> createStockMovement(@RequestBody StockMovementRequest stockMovement);

  @Operation(
      summary = "Get stock",
      description = "Get stock for all products",
      tags = {"Stock"})
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
      })
  ResponseEntity<List<StockEntityModel>> getStock();

  @Operation(
      summary = "Get a product stock",
      description = "Returns a particular product stock or empty",
      tags = {"Stock"})
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
  ResponseEntity<StockEntityModel> getStock(
      @Parameter(name = "uuid", description = "The product uuid", in = ParameterIn.PATH) UUID uuid);
}
