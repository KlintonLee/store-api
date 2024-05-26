package com.klinton.store.infrastructure.cart.api;

import com.klinton.store.infrastructure.cart.dto.CreateCartDto;
import com.klinton.store.infrastructure.cart.dto.UpdateCartDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart")
@Tag(name = "Cart")
public interface CartApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createCart(CreateCartDto dto);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a carts by customer identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    GetCartsResponse getCartByCustomerId(String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a cart by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updateCart(String id, UpdateCartDto dto);

    @DeleteMapping(
            value = "{id}"
    )
    @Operation(summary = "Delete a cart by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> deleteCart(String id);
}
