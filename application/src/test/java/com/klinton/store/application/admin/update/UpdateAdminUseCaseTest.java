package com.klinton.store.application.admin.update;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
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

    private final static String EXPECT_PASSWORD = "123456";

    private final static boolean EXPECTED_ACTIVE = true;

    @InjectMocks
    private DefaultUpdateAdminUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateAdminUseCase_shouldReturnVoid() {
        // Arrange
        final var admin = Admin.create("Jane Doe", "jane.doe@fake_email.com", "654321", false);
        final var adminId = admin.getId();
        final var createdAt = admin.getCreatedAt();
        final var updatedAt = admin.getUpdatedAt();

        final var command = UpdateAdminCommand.with(adminId.getValue(), EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECTED_ACTIVE);
        when(adminGateway.getById(adminId)).thenReturn(Optional.of(admin));
        when(adminGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        final var output = useCase.execute(command);

        // Assert
        assertNotNull(output);
        assertEquals(adminId.getValue(), output.id());
        verify(adminGateway, times(1)).save(argThat(anAdmin ->
                EXPECTED_NAME.equals(anAdmin.getName())
                && EXPECTED_EMAIL.equals(anAdmin.getEmail())
                && EXPECT_PASSWORD.equals(anAdmin.getPassword())
                && EXPECTED_ACTIVE == anAdmin.isActive()
                && createdAt.equals(anAdmin.getCreatedAt())
                && updatedAt.isBefore(anAdmin.getUpdatedAt())
                && anAdmin.getDeletedAt() == null
        ));
    }
}
