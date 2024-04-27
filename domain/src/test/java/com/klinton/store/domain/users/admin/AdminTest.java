package com.klinton.store.domain.users.admin;

import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static boolean EXPECTED_ACTIVE = true;

    @Test
    public void givenValidParams_whenCallCreate_thenShouldReturnAdmin() {
        // Act
        final var admin = Admin.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECTED_ACTIVE);

        // Assert
        assertNotNull(admin);
        assertNotNull(admin.getId());
        assertEquals(EXPECTED_NAME, admin.getName());
        assertEquals(EXPECTED_EMAIL, admin.getEmail());
        assertEquals(EXPECT_PASSWORD, admin.getPassword());
        assertEquals(EXPECTED_ACTIVE, admin.isActive());
        assertNotNull(admin.getCreatedAt());
        assertNotNull(admin.getUpdatedAt());
        assertNull(admin.getDeletedAt());
    }

    @Test
    public void givenAnInactiveAdmin_whenCallCreate_thenShouldReturnInactiveAdmin() {
        // Arrange
        final var expectedActive = false;

        // Act
        final var admin = Admin.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, expectedActive);

        // Assert
        assertFalse(admin.isActive());
        assertNotNull(admin.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallNewAdmin_thenShouldThrowException() {
        // Arrange
        final String nullName = null;
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var admin = Admin.create(nullName, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECTED_ACTIVE);
        final var exception = assertThrows(DomainException.class, () -> admin.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyName_whenCallNewAdmin_thenShouldThrowException() {
        // Arrange
        final var nullName = " ";
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var admin = Admin.create(nullName, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECTED_ACTIVE);
        final var exception = assertThrows(DomainException.class, () -> admin.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullEmail_whenCallNewAdmin_thenShouldThrowException() {
        // Arrange
        final String nullEmail = null;
        final var expectedErrorMessage = "Email should not be null or empty";

        // Act
        final var admin = Admin.create(EXPECTED_NAME, nullEmail, EXPECT_PASSWORD, EXPECTED_ACTIVE);
        final var exception = assertThrows(DomainException.class, () -> admin.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyEmail_whenCallNewAdmin_thenShouldThrowException() {
        // Arrange
        final String emptyEmail = " ";
        final var expectedErrorMessage = "Email should not be null or empty";

        // Act
        final var admin = Admin.create(EXPECTED_NAME, emptyEmail, EXPECT_PASSWORD, EXPECTED_ACTIVE);
        final var exception = assertThrows(DomainException.class, () -> admin.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
