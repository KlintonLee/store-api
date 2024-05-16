package com.klinton.store.infrastructure.purchase.api;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.infrastructure.purchase.dto.CreatePurchaseDto;
import com.klinton.store.infrastructure.purchase.dto.UpdatePurchaseDto;
import com.klinton.store.infrastructure.purchase.presenter.GetPurchaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/purchases")
@Tag(name = "Purchase")
public interface PurchaseApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new purchase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createPurchase(@RequestBody CreatePurchaseDto dto);

    @GetMapping
    @Operation(summary = "List all purchases paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<?> listPurchases(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "status") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a Purchase by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Purchase was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    GetPurchaseResponse getPurchaseById(@PathVariable String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Purchase by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "404", description = "Purchase was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updatePurchase(@PathVariable String id, @RequestBody UpdatePurchaseDto dto);

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Delete a Purchase by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Purchase was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> deletePurchase(@PathVariable String id);
}
