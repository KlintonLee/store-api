package com.klinton.store.application.admin.update;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.exception.NotFoundException;
import com.klinton.store.domain.exception.UnprocessableEntityException;
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
public class UpdateAdminUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static boolean EXPECTED_ACTIVE = true;

    @InjectMocks
    private DefaultUpdateAdminUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateAdminUseCase_shouldReturnVoid() throws InterruptedException {
        // Arrange
        final var admin = Admin.create("Jane Doe", "jane.doe@fake_email.com", "654321", false);
        final var adminId = admin.getId();
        final var createdAt = admin.getCreatedAt();
        final var updatedAt = admin.getUpdatedAt();

        final var command = UpdateAdminCommand.with(adminId.getValue(), EXPECTED_NAME, EXPECTED_EMAIL, EXPECTED_ACTIVE);
        when(adminGateway.getById(adminId)).thenReturn(Optional.of(admin));
        when(adminGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        Thread.sleep(1);
        final var output = useCase.execute(command);

        // Assert
        assertNotNull(output);
        assertEquals(adminId.getValue(), output.id());
        verify(adminGateway, times(1)).save(argThat(anAdmin ->
                EXPECTED_NAME.equals(anAdmin.getName())
                && EXPECTED_EMAIL.equals(anAdmin.getEmail())
                && EXPECTED_ACTIVE == anAdmin.isActive()
                && createdAt.equals(anAdmin.getCreatedAt())
                && updatedAt.isBefore(anAdmin.getUpdatedAt())
                && anAdmin.getDeletedAt() == null
        ));
    }

    @Test
    public void givenAnInvalidCommandWithNullName_whenCallUpdateAdminUseCase_shouldThrowException() {
        // Arrange
        final var admin = Admin.create("Jane Doe", "jane.doe@fake_email.com", "654321", false);
        final var adminId = admin.getId();
        final var expectedErrorMessage = "Name should not be null or empty";

        final var command = UpdateAdminCommand.with(adminId.getValue(), null, EXPECTED_EMAIL, EXPECTED_ACTIVE);
        when(adminGateway.getById(adminId)).thenReturn(Optional.of(admin));

        // Act
        final var exception = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenACommandWithNonExistingAdmin_whenCallUpdateAdminUseCase_shouldThrowException() {
        // Arrange
        final var adminId = "invalid_id";
        final var expectedErrorMessage = "Admin with ID invalid_id was not found.";

        final var command = UpdateAdminCommand.with(adminId, null, EXPECTED_EMAIL, EXPECTED_ACTIVE);
        when(adminGateway.getById(any())).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var admin = Admin.create("Jane Doe", "jane.doe@fake_email.com", "654321", false);
        final var adminId = admin.getId();
        final var expectedErrorMessage = "Gateway error";

        final var command = UpdateAdminCommand.with(adminId.getValue(), EXPECTED_NAME, EXPECTED_EMAIL, EXPECTED_ACTIVE);
        when(adminGateway.getById(adminId)).thenReturn(Optional.of(admin));
        when(adminGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
