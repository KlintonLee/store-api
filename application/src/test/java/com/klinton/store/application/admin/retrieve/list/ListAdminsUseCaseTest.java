package com.klinton.store.application.admin.retrieve.list;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
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
public class ListAdminsUseCaseTest {

    @InjectMocks
    private DefaultListAdminsUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidSearchQuery_whenCallListAdminsUseCase_shouldReturnAListOfAdmins() {
        // Arrange
        final var expectedItemsCount = 2;
        final var expectedPage = 1;
        final var expectedPerPage = 10;
        final var expectedResult = Pagination.of(1, 10, 2, List.of(
                Admin.create("Jane Doe", "jane.doe@fake_email.com", "654321", false),
                Admin.create("John Doe", "john.doe@fake_email.com", "123456", true)
        ));

        final var query = SearchQuery.of(expectedPage, expectedPerPage, "", "name", "asc");
        when(adminGateway.getAll(query)).thenReturn(expectedResult);

        final var expectedAdmins = expectedResult.items().stream().map(ListAdminOutput::from).toList();
        // Act
        final var output = useCase.execute(query);

        // Assert
        Assertions.assertEquals(expectedItemsCount, output.total());
        Assertions.assertEquals(expectedAdmins, output.items());
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
    }

}
