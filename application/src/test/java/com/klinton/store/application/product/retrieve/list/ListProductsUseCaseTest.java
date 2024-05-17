package com.klinton.store.application.product.retrieve.list;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListProductsUseCaseTest {

    @InjectMocks
    private DefaultListProductsUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void givenAValidSearchQuery_whenCallListProductsUseCase_shouldReturnAListOfProducts() {
        // Arrange
        final var expectedItemsCount = 2;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(1, 10, 2, List.of(
                Product.create("Product 1", "Description 1", 10, 100.0, true),
                Product.create("Product 2", "Description 2", 20, 200.0, true)
        ));
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(productGateway.getAll(query)).thenReturn(expectedResult);

        final var expectedProducts = expectedResult.items().stream().map(ListProductOutput::from).toList();

        // Act
        final var output = useCase.execute(query);

        // Assert
        Assertions.assertEquals(expectedItemsCount, output.total());
        Assertions.assertEquals(expectedProducts, output.items());
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyProducts() {
        // Arrange
        final var products = List.<Product>of();
        final var expectedItemsCount = 0;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(expectedPage, expectedPerPage, expectedItemsCount, products);

        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(productGateway.getAll(query)).thenReturn(expectedResult);
        final var expectedProducts = expectedResult.items().stream().map(ListProductOutput::from).toList();

        // Act
        final var output = useCase.execute(query);

        // Assert
        Assertions.assertEquals(expectedItemsCount, output.total());
        Assertions.assertEquals(expectedProducts, output.items());
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        final var expectedErrorMessage = "Gateway error";
        when(productGateway.getAll(query)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(query));

        // Assert
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
