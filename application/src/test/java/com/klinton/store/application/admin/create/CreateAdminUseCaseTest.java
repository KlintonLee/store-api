package com.klinton.store.application.admin.create;

import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAdminUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static boolean EXPECTED_ACTIVE = true;

    @InjectMocks
    private DefaultCreateAdminUseCase useCase;

    @Mock
    private AdminGateway gateway;

    @Test
    public void givenValidCommand_whenCallsCreateAdminExecute_ShouldCreateAdmin() {
        // Arrange
        final var command = CreateAdminCommand.with(
                EXPECTED_NAME,
                EXPECTED_EMAIL,
                EXPECT_PASSWORD,
                EXPECTED_ACTIVE
        );
        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // Act
        final var admin = useCase.execute(command);

        // Assert
        assertNotNull(admin);
        assertNotNull(admin.id());

        verify(gateway, times(1)).create(argThat(anAdmin ->
                EXPECTED_NAME.equals(anAdmin.getName())
                        && EXPECTED_EMAIL.equals(anAdmin.getEmail())
                        && EXPECT_PASSWORD.equals(anAdmin.getPassword())
                        && EXPECTED_ACTIVE == anAdmin.isActive()
        ));
    }

    @Test
    public void givenACommandWithNullOrEmptyName_whenCallCreateAdminExecute_ShouldThrowUnprocessableEntityException() {
        // Arrange
        final var expectedErrorMessage = "Name should not be null or empty";
        final var nullNameCommand = CreateAdminCommand.with(
                null,
                EXPECTED_EMAIL,
                EXPECT_PASSWORD,
                EXPECTED_ACTIVE
        );
        final var emptyNameCommand = CreateAdminCommand.with(
                " ",
                EXPECTED_EMAIL,
                EXPECT_PASSWORD,
                EXPECTED_ACTIVE
        );

        // Act
        final var nullNameException = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(nullNameCommand));
        final var emptyNameException = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(emptyNameCommand));

        // Assert
        assertEquals(expectedErrorMessage, nullNameException.getMessage());
        assertEquals(expectedErrorMessage, emptyNameException.getMessage());
    }
}
