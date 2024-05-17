package com.klinton.store.infrastructure.products.api;

import com.klinton.store.domain.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@Tag(name = "Products")
public interface ProductApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createProduct(@RequestBody CreateProductDto dto);

    @GetMapping
    @Operation(summary = "List all products paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<?> listProducts(
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
    @Operation(summary = "Get a product by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    GetProductResponse getProductById(@PathVariable String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Product by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prodcuct retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Prodcuct was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @RequestBody UpdateProductDto dto
    );

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Delete a product by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<Void> deleteProduct(@PathVariable String id);
}
