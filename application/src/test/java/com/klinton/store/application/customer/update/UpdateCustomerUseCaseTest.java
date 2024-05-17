package com.klinton.store.application.customer.update;

import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.exception.NotFoundException;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCustomerUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static String EXPECT_PHONE = "123456789";

    @InjectMocks
    private DefaultUpdateCustomerUseCase useCase;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidCommand_whenCallUpdate_thenShouldUpdateCustomer() throws InterruptedException {
        // Arrange
        final var customer = Customer.create("JANE DOE", "jane.doe@fake_email.com", EXPECT_PASSWORD, "987654321");
        final var customerId = customer.getId();
        final var createdAt = customer.getCreatedAt();
        final var updatedAt = customer.getUpdatedAt();
        final var command = UpdateCustomerCommand.of(
                customerId.getValue(),
                EXPECTED_NAME,
                EXPECTED_EMAIL,
                EXPECT_PHONE,
                false
        );

        when(customerGateway.getById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerGateway.save(customer)).thenAnswer(returnsFirstArg());

        // Act
        Thread.sleep(1);
        final var output = useCase.execute(command);

        // Assert
        assertNotNull(output);
        verify(customerGateway, times(1)).save(argThat(aCustomer ->
                Objects.equals(EXPECTED_NAME, aCustomer.getName()) &&
                Objects.equals(EXPECTED_EMAIL, aCustomer.getEmail()) &&
                Objects.equals(EXPECT_PASSWORD, aCustomer.getPassword()) &&
                Objects.equals(EXPECT_PHONE, aCustomer.getPhone()) &&
                !aCustomer.isActive() &&
                Objects.equals(createdAt, aCustomer.getCreatedAt()) &&
                updatedAt.isBefore(aCustomer.getUpdatedAt()) &&
                aCustomer.getDeletedAt() != null
        ));
    }

    @Test
    public void givenAnInvalidCommandWithNullName_whenCallUpdateCustomerUseCase_shouldThrowException() {
        // Arrange
        final var customer = Customer.create("JANE DOE", "jane.doe@fake_email.com", EXPECT_PASSWORD, "987654321");
        final var customerId = customer.getId();
        final var expectedErrorMessage = "Name should not be null or empty";

        final var command = UpdateCustomerCommand.of(
                customerId.getValue(),
                null,
                EXPECTED_EMAIL,
                EXPECT_PHONE,
                false
        );

        when(customerGateway.getById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        final var exception = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenACommandWithNonExistingCustomer_whenCallUpdateCustomerUseCase_shouldThrowNotFoundException() {
        // Arrange
        final var customerId = "invalid_id";
        final var expectedErrorMessage = "Customer with ID invalid_id was not found.";
        final var command = UpdateCustomerCommand.of(
                customerId,
                EXPECTED_NAME,
                EXPECTED_EMAIL,
                EXPECT_PHONE,
                false
        );

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var customer = Customer.create("JANE DOE", "jane.doe@fake_email.com", EXPECT_PASSWORD, "987654321");
        final var customerId = customer.getId();
        final var command = UpdateCustomerCommand.of(
                customerId.getValue(),
                EXPECTED_NAME,
                EXPECTED_EMAIL,
                EXPECT_PHONE,
                false
        );

        final var expectedErrorMessage = "Gateway error";

        when(customerGateway.getById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
