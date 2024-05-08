package com.klinton.store.application.admin.delete;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.core.admin.AdminID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAdminUseCaseTest {

    @InjectMocks
    private DefaultDeleteAdminUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidCommand_whenCallDeleteAdminUseCase_shouldReturnVoid() throws InterruptedException {
        // Arrange
        final var id = "valid_admin_id";
        final var adminId = AdminID.from(id);
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@fake_email.com";
        final var expectedPassword = "123456";
        final var expectedIsActive = true;
        final var admin = Admin.create(expectedName, expectedEmail, expectedPassword, expectedIsActive);
        final var createdAt = admin.getCreatedAt();
        final var updatedAt = admin.getUpdatedAt();

        when(adminGateway.getById(adminId)).thenReturn(Optional.of(admin));
        when(adminGateway.update(admin)).thenAnswer(returnsFirstArg());

        // Act
        Thread.sleep(1);
        assertDoesNotThrow(() -> useCase.execute(id));

        // Assert
        assertEquals(expectedName, admin.getName());
        assertEquals(expectedEmail, admin.getEmail());
        assertEquals(expectedPassword, admin.getPassword());
        assertFalse(admin.isActive());
        assertEquals(createdAt, admin.getCreatedAt());
        assertTrue(updatedAt.isBefore(admin.getUpdatedAt()));
        assertNotNull(admin.getDeletedAt());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_shouldReturnVoid() throws InterruptedException {
        // Arrange
        final var id = "valid_admin_id";
        final var adminId = AdminID.from(id);
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@fake_email.com";
        final var expectedPassword = "123456";
        final var expectedIsActive = true;
        final var admin = Admin.create(expectedName, expectedEmail, expectedPassword, expectedIsActive);
        final var expectedErrorMessage = "Gateway error";

        when(adminGateway.getById(adminId)).thenReturn(Optional.of(admin));
        when(adminGateway.update(admin)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        Thread.sleep(1);
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(id));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
