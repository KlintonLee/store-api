package com.klinton.store.application.customer.delete;

import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteCustomerUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static String EXPECT_PHONE = "123456789";

    @InjectMocks
    private DefaultDeleteCustomerUseCase useCase;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenValidCustomerId_whenCallDeleteCustomerUseCase_thenShouldDeleteCustomer() throws InterruptedException {
        // Arrange
        final var id = "valid_admin_id";
        final var customerID = CustomerID.from(id);
        final var customer = Customer.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        final var createdAt = customer.getCreatedAt();
        final var updatedAt = customer.getUpdatedAt();

        when(customerGateway.getById(customerID)).thenReturn(Optional.of(customer));
        when(customerGateway.save(customer)).thenAnswer(returnsFirstArg());

        // Act
        Thread.sleep(1);
        assertDoesNotThrow(() -> useCase.execute(id));

        // Assert
        assertEquals(EXPECTED_NAME, customer.getName());
        assertEquals(EXPECTED_EMAIL, customer.getEmail());
        assertEquals(EXPECT_PASSWORD, customer.getPassword());
        assertEquals(EXPECT_PHONE, customer.getPhone());
        assertFalse(customer.isActive());
        assertEquals(createdAt, customer.getCreatedAt());
        assertTrue(updatedAt.isBefore(customer.getUpdatedAt()));
        assertNotNull(customer.getDeletedAt());
    }

    @Test
    public void givenAValidString_whenGatewayThrowsException_shouldReturnVoid() throws InterruptedException {
        // Arrange
        final var id = "valid_admin_id";
        final var customerID = CustomerID.from(id);
        final var expectedErrorMessage = "Gateway error";

        when(customerGateway.getById(customerID)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        Thread.sleep(1);
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(id));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
