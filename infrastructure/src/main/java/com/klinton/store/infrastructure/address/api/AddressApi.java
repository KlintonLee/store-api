package com.klinton.store.infrastructure.address.api;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.infrastructure.address.dto.CreateAddressDto;
import com.klinton.store.infrastructure.address.dto.UpdateAddressDto;
import com.klinton.store.infrastructure.address.presenter.GetAddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/addresses")
@Tag(name = "Address")
public interface AddressApi {

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created successfully"),
        @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
        @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createAddress(@RequestBody CreateAddressDto input);

    @GetMapping
    @Operation(summary = "List all addresses paginated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listed successfully"),
        @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<?> listAddresses(
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
    @Operation(summary = "Get a Address by it's identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Address retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Address was not found"),
        @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    GetAddressResponse getAddressById(@PathVariable String id);

    @PutMapping(
        value = "{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Address by it's identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Address updated successfully"),
        @ApiResponse(responseCode = "404", description = "Address was not found"),
        @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updateAddress(@PathVariable String id, @RequestBody UpdateAddressDto input);

    @DeleteMapping(
        value = "{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Delete a Address by it's identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Address deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Address was not found"),
        @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> deleteAddress(@PathVariable String id);
}
