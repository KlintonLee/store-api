package com.klinton.store.application.admin.delete;

import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.core.admin.AdminID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAdminUseCaseTest {

    @InjectMocks
    private DefaultDeleteAdminUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidCommand_whenCallDeleteAdminUseCase_shouldReturnVoid() {
        // Arrange
        final var id = "valid_admin_id";
        final var adminId = AdminID.from(id);
        doNothing().when(adminGateway).delete(adminId);

        // Act
        assertDoesNotThrow(() -> useCase.execute(id));

        // Assert
        verify(adminGateway, times(1)).delete(adminId);
    }
}
