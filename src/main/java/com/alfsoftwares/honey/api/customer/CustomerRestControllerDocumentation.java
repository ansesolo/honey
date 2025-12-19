package com.alfsoftwares.honey.api.customer;

import com.alfsoftwares.honey.api.customer.application.model.CustomerEntityModel;
import com.alfsoftwares.honey.api.customer.domain.model.CustomerEntity;
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
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "Bearer Authentication")
public interface CustomerRestControllerDocumentation {

  @Operation(
      summary = "Get all customers",
      description = "Returns a list of customer",
      tags = {"Customers"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content =
                @Content(
                    mediaType = "application/hal+json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerEntity.class)))),
        @ApiResponse(
            responseCode = "403",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The customers was not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)))
      })
  ResponseEntity<List<CustomerEntityModel>> getCustomers();

  @Operation(
      summary = "Get a customer",
      description = "Returns a particular customer or empty",
      tags = {"Customers"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerEntity.class)))),
        @ApiResponse(
            responseCode = "403",
            description = "Not authorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The customer was not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)))
      })
  ResponseEntity<CustomerEntityModel> getCustomer(
      @Parameter(name = "id", description = "The customer id", in = ParameterIn.PATH) Long id);
}
