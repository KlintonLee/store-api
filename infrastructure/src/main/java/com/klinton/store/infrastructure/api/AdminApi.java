package com.klinton.store.infrastructure.api;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.infrastructure.models.CreateAdminDto;
import com.klinton.store.infrastructure.models.GetAdminOutput;
import com.klinton.store.infrastructure.models.UpdateAdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@Tag(name = "Admin")
public interface AdminApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createAdmin(@RequestBody CreateAdminDto input);

    @GetMapping
    @Operation(summary = "List all admins paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<?> listCategories(
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
    @Operation(summary = "Get a admin by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Admin was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    GetAdminOutput getAdminById(@PathVariable String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Admin by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Admin was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was  thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> updateAdmin(
            @PathVariable String id,
            @RequestBody UpdateAdminDto input
    );

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Delete a admin by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Admin deleted successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<Void> deleteAdmin(@PathVariable String id);
}
