package com.alfsoftwares.honey.product.internal.application;

import com.alfsoftwares.honey.product.internal.application.model.EnumValue;
import com.alfsoftwares.honey.product.internal.application.model.ProductEntityModel;
import com.alfsoftwares.honey.product.internal.domain.model.ProductEntity;
import com.alfsoftwares.honey.product.internal.domain.model.RequestProduct;
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
public interface ProductRestControllerDocumentation {

  @Operation(
      summary = "Get enum values",
      description = "Returns a list enum values",
      tags = {"Products"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EnumValue.class)))),
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
  ResponseEntity<List<EnumValue>> getEnumValues(
      @Parameter(name = "name", description = "The enum name", in = ParameterIn.PATH) String name);

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
      @Parameter(name = "uuid", description = "The product uuid", in = ParameterIn.PATH) UUID uuid);

  @Operation(
      summary = "Create a product",
      description = "Create a product",
      tags = {"Products"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Successfully created",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))),
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
      })
  ResponseEntity<ProductEntityModel> createProduct(@RequestBody RequestProduct product);

  @Operation(
      summary = "Update a product",
      description = "Update a product",
      tags = {"Products"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))),
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
            description = "Not found - The product was not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class))),
      })
  ResponseEntity<ProductEntityModel> updateProduct(
      @Parameter(name = "uuid", description = "The product uuid", in = ParameterIn.PATH) UUID uuid,
      @RequestBody RequestProduct product);

  @Operation(
      summary = "Delete a product",
      description = "Delete a product",
      tags = {"Products"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully deleted",
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
            description = "Not found - The product was not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class))),
      })
  ResponseEntity<ProductEntityModel> deleteProduct(
      @Parameter(name = "uuid", description = "The product uuid", in = ParameterIn.PATH) UUID uuid);
}
