package com.klinton.store.infrastructure.customer.api;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.infrastructure.customer.dto.CreateCustomerDto;
import com.klinton.store.infrastructure.customer.dto.UpdateCustomerDto;
import com.klinton.store.infrastructure.customer.presenter.GetCustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customers")
@Tag(name = "Customer")
public interface CustomerApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createCustomer(@RequestBody CreateCustomerDto input);

    @GetMapping
    @Operation(summary = "List all customers paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<?> listCustomers(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a Customer by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    GetCustomerResponse getCustomerById(@PathVariable String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Customer by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updateCustomer(
            @PathVariable String id,
            @RequestBody UpdateCustomerDto input
    );

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Delete a Customer by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<Void> deleteCustomer(@PathVariable String id);
}
