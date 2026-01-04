package com.alfsoftwares.honey.customer.internal.application;

import com.alfsoftwares.honey.customer.internal.application.model.CustomerEntityModel;
import com.alfsoftwares.honey.customer.internal.domain.model.CustomerEntity;
import com.alfsoftwares.honey.customer.internal.domain.model.RequestCustomer;
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
                    schema = @Schema(implementation = ProblemDetail.class)))
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
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  ResponseEntity<CustomerEntityModel> getCustomer(
      @Parameter(name = "uuid", description = "The customer uuid", in = ParameterIn.PATH)
          UUID uuid);

  @Operation(
      summary = "Create a customer",
      description = "Create a customer",
      tags = {"Customers"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Successfully created",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerEntity.class)))),
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
  ResponseEntity<CustomerEntityModel> createCustomer(@RequestBody RequestCustomer customer);

  @Operation(
      summary = "Update a customer",
      description = "Update a customer",
      tags = {"Customers"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CustomerEntity.class)))),
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
            description = "Not found - The customer was not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class))),
      })
  ResponseEntity<CustomerEntityModel> updateCustomer(
      @Parameter(name = "uuid", description = "The customer uuid", in = ParameterIn.PATH) UUID uuid,
      @RequestBody RequestCustomer customer);

  @Operation(
      summary = "Delete a customer",
      description = "Delete a customer",
      tags = {"Customers"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully deleted",
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
                    schema = @Schema(implementation = ProblemDetail.class))),
      })
  ResponseEntity<CustomerEntityModel> deleteCustomer(
      @Parameter(name = "uuid", description = "The customer uuid", in = ParameterIn.PATH)
          UUID uuid);
}
