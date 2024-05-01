package com.klinton.store.domain.users.customer;

import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private final static String NAME = "John Doe";

    private final static String EMAIL = "johndoe@email.com";

    private final static String PASSWORD = "hashed_password";

    private final static String PHONE = "1234567890";

    @Test
    public void givenAValidParams_whenCallingCreate_thenShouldReturnACustomer() {
        // Act
        var customer = Customer.create(NAME, EMAIL, PASSWORD, PHONE);

        // Assert
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals(NAME, customer.getName());
        assertEquals(EMAIL, customer.getEmail());
        assertEquals(PASSWORD, customer.getPassword());
        assertEquals(PHONE, customer.getPhone());
        assertTrue(customer.isActive());
        assertNotNull(customer.getCreatedAt());
        assertNotNull(customer.getUpdatedAt());
        assertNull(customer.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallingCreate_thenShouldThrowException() {
        // Arrange
        String nullName = null;
        var expectedErrorMessage = "Name should not be null or empty";

        // Act
        var customer = Customer.create(nullName, EMAIL, PASSWORD, PHONE);
        var exception = assertThrows(DomainException.class, () -> customer.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyName_whenCallingCreate_thenShouldThrowException() {
        // Arrange
        String emptyName = " ";
        var expectedErrorMessage = "Name should not be null or empty";

        // Act
        var customer = Customer.create(emptyName, EMAIL, PASSWORD, PHONE);
        var exception = assertThrows(DomainException.class, () -> customer.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
