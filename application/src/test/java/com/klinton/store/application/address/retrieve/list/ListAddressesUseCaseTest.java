package com.klinton.store.application.address.retrieve.list;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.States;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListAddressesUseCaseTest {

    @InjectMocks
    private DefaultListAddressesUseCase useCase;

    @Mock
    private AddressGateway addressGateway;

    @Test
    public void givenAValidSearchQuery_whenCallListAddresses_thenShouldReturnListOfAddresses() {
        // Arrange
        final var expectedItemsCount = 2;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var addresses = List.of(
                Address.create("123", "street_1", "city_1", "neighborhood_1", States.AP, "1A", "12345678"),
                Address.create("456", "street_2", "city_2", "neighborhood_2", States.SP, "2A", "87654321")
        );
        final var pagination = Pagination.of(1, 10, 2, addresses);
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "street", "asc");

        when(addressGateway.getAll(query)).thenReturn(pagination);
        final var expectedAddresses = pagination.items().stream().map(ListAddressOutput::from).toList();

        // Act
        final var output = useCase.execute(query);

        // Assert
        assertEquals(expectedItemsCount, output.total());
        assertEquals(expectedAddresses, output.items());
        assertEquals(expectedPage, output.currentPage());
        assertEquals(expectedPerPage, output.perPage());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyAddresses() {
        // Arrange
        final var addresses = List.<Address>of();
        final var expectedItemsCount = 2;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var pagination = Pagination.of(1, 10, 2, addresses);
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "street", "asc");

        when(addressGateway.getAll(query)).thenReturn(pagination);
        final var expectedAddresses = pagination.items().stream().map(ListAddressOutput::from).toList();

        // Act
        final var output = useCase.execute(query);

        // Assert
        assertEquals(expectedItemsCount, output.total());
        assertEquals(expectedAddresses, output.items());
        assertEquals(expectedPage, output.currentPage());
        assertEquals(expectedPerPage, output.perPage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "street", "asc");
        final var expectedErrorMessage = "Gateway error";

        when(addressGateway.getAll(query)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(query));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
