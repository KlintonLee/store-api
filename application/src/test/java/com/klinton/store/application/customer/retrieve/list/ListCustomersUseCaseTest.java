package com.klinton.store.application.customer.retrieve.list;

import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
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
public class ListCustomersUseCaseTest {

    @InjectMocks
    private DefaultListCustomersUseCase useCase;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidSearchQuery_whenCallListCustomersUseCase_shouldReturnAListOfCustomers() {
        // Arrange
        final var expectedItemsCount = 2;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(1, 10, 2, List.of(
                Customer.create("Jane Doe", "jane.doe@fake_email.com", "654321", "123456789"),
                Customer.create("John Doe", "john.doe@fake_email.com", "123456", "987654321")
        ));
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(customerGateway.getAll(query)).thenReturn(expectedResult);

        final var expectedCustomers = expectedResult.items().stream().map(ListCustomerOutput::from).toList();

        // Act
        final var output = useCase.execute(query);

        // Assert
        Assertions.assertEquals(expectedItemsCount, output.total());
        Assertions.assertEquals(expectedCustomers, output.items());
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyCustomers() {
        final var customers = List.<Customer>of();
        final var expectedItemsCount = 0;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(expectedPage, expectedPerPage, expectedItemsCount, customers);

        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(customerGateway.getAll(query)).thenReturn(expectedResult);
        final var expectedCustomers = expectedResult.items().stream().map(ListCustomerOutput::from).toList();

        // Act
        final var output = useCase.execute(query);

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
        when(customerGateway.getAll(query)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(query));

        // Assert
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
