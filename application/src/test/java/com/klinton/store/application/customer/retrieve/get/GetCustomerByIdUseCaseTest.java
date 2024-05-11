package com.klinton.store.application.customer.retrieve.get;

import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
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
public class GetCustomerByIdUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static String EXPECT_PHONE = "123456789";

    @InjectMocks
    private DefaultGetCustomerByIdUseCase useCase;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidId_whenCallGetById_thenShouldReturnCustomer() {
        // Arrange
        final var customer = Customer.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        when(customerGateway.getById(customer.getId())).thenReturn(Optional.of(customer));

        // Act
        final var output = useCase.execute(customer.getId().getValue());

        // Assert
        assertNotNull(output);
        assertEquals(EXPECTED_NAME, output.name());
        assertEquals(EXPECTED_EMAIL, output.email());
        assertEquals(EXPECT_PHONE, output.phone());
        assertNotNull(output.createdAt());
        assertNotNull(output.updatedAt());
        assertNull(output.deletedAt());
    }

    @Test
    public void givenANonExistingCustomerId_whenCallsGetById_thenShouldThrowNotFoundException() {
        // Arrange
        when(customerGateway.getById(any())).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Customer with ID 123 was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute("123"));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
