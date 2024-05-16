package com.klinton.store.application.purchase.retrieve.list;

import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseStatus;
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
public class ListPurchaseUseCaseTest {

    @InjectMocks
    private DefaultListPurchasesUseCase listPurchaseUseCase;

    @Mock
    private PurchaseGateway purchaseGateway;

    @Test
    public void givenAValidSearchQuery_whenCallListPurchasesUseCase_shouldReturnAListOfPurchases() {
        // Arrange
        final var expectedItemsCount = 2;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(expectedPage, expectedPerPage, expectedItemsCount, List.of(
                Purchase.with("customer_id", "address_id", 10.0, "boleto", PurchaseStatus.COMPLETED),
                Purchase.with("customer_id_2", "address_id_2", 15.0, "credit card", PurchaseStatus.COMPLETED)
        ));
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(purchaseGateway.getAll(query)).thenReturn(expectedResult);

        final var expectedCustomers = expectedResult.items().stream().map(ListPurchaseOutput::from).toList();

        // Act
        final var output = listPurchaseUseCase.execute(query);

        // Assert
        Assertions.assertEquals(expectedItemsCount, output.total());
        Assertions.assertEquals(expectedCustomers, output.items());
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
    }

    @Test
    public void givenAValidSearchQuery_whenHasNoResults_thenShouldReturnEmptyPurchases() {
        // Arrange
        final var expectedItemsCount = 0;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(expectedPage, expectedPerPage, expectedItemsCount, List.<Purchase>of());
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(purchaseGateway.getAll(query)).thenReturn(expectedResult);

        final var expectedCustomers = expectedResult.items().stream().map(ListPurchaseOutput::from).toList();

        // Act
        final var output = listPurchaseUseCase.execute(query);

        // Assert
        Assertions.assertEquals(expectedItemsCount, output.total());
        Assertions.assertEquals(expectedCustomers, output.items());
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
        when(purchaseGateway.getAll(query)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = Assertions.assertThrows(IllegalStateException.class, () -> listPurchaseUseCase.execute(query));

        // Assert
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
