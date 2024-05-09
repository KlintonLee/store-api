package com.klinton.store.application.admin.retrieve.get;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAdminByIdUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static boolean EXPECTED_ACTIVE = true;

    @InjectMocks
    private DefaultGetAdminByIdUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidId_whenCallGetById_thenShouldReturnAdmin() {
        // Arrange
        final var admin = Admin.create(EXPECTED_NAME, EXPECTED_EMAIL, "123456", EXPECTED_ACTIVE);
        when(adminGateway.getById(admin.getId())).thenReturn(Optional.of(admin));

        // Act
        final var output = useCase.execute(admin.getId().getValue());

        // Assert
        assertNotNull(output);
        assertEquals(EXPECTED_NAME, output.name());
        assertEquals(EXPECTED_EMAIL, output.email());
        assertEquals(EXPECTED_ACTIVE, output.active());
        assertNotNull(output.createdAt());
        assertNotNull(output.updatedAt());
        assertNull(output.deletedAt());
    }

    @Test
    public void givenANonExistingAdminId_whenCallsGetById_thenShouldThrowNotFoundException() {
        // Arrange
        when(adminGateway.getById(any())).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Admin with ID 123 was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute("123"));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var expectedErrorMessage = new IllegalStateException("Gateway error");
        final var admin = Admin.create(EXPECTED_NAME, EXPECTED_EMAIL, "123456", EXPECTED_ACTIVE);
        when(adminGateway.getById(admin.getId())).thenThrow(expectedErrorMessage);

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(admin.getId().getValue()));

        // Assert
        assertEquals(expectedErrorMessage, exception);
    }
}
