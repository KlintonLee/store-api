package com.klinton.store.application.customer.create;

import com.klinton.store.application.admin.create.CreateAdminCommand;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static String EXPECT_PHONE = "123456789";

    @InjectMocks
    private DefaultCreateCustomerUseCase useCase;

    @Mock
    private CustomerGateway gateway;

    @Test
    public void givenValidCommand_whenCallCreateCustomerUseCase_thenShouldReturnCustomer() {
        // Arrange
        final var command = new CreateCustomerCommand(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        when(gateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        useCase.execute(command);

        // Assert
        verify(gateway, times(1)).save(argThat(customer ->
                Objects.equals(customer.getName(), EXPECTED_NAME) &&
                Objects.equals(customer.getEmail(), EXPECTED_EMAIL) &&
                Objects.equals(customer.getPassword(), EXPECT_PASSWORD) &&
                Objects.equals(customer.getPhone(), EXPECT_PHONE) &&
                customer.isActive() &&
                Objects.nonNull(customer.getCreatedAt()) &&
                Objects.nonNull(customer.getUpdatedAt()) &&
                Objects.isNull(customer.getDeletedAt()
        )));
    }

    @Test
    public void givenACommandWithNullName_whenCallCreateCustomerExecute_ShouldThrowExceptionFromNotification() {
        // Arrange
        final var expectedErrorMessage = "Name should not be null or empty";
        final var command = new CreateCustomerCommand(null, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);

        // Act
        final var exception = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenValidCommand_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var expectedErrorMessage = "Gateway error";
        final var command = new CreateCustomerCommand(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        when(gateway.save(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
