package com.klinton.store.domain.product;

import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private final static String EXPECTED_NAME = "Product Name";

    private final static String EXPECTED_DESCRIPTION = "Product Description";

    private final static int EXPECTED_QUANTITY = 10;

    private final static double EXPECTED_PRICE = 100.0;

    @Test
    public void givenValidParams_whenCallCreate_thenShouldReturnANewOne() {
        // Act
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(EXPECTED_NAME, product.getName());
        assertEquals(EXPECTED_DESCRIPTION, product.getDescription());
        assertEquals(EXPECTED_QUANTITY, product.getQuantity());
        assertEquals(EXPECTED_PRICE, product.getPrice());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNull(product.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallCreate_thenShouldThrownAnException() {
        // Arrange
        final String nullName = null;
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var product = Product.create(nullName, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyName_whenCallCreate_thenShouldThrownAnException() {
        // Arrange
        final var emptyName = " ";
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var product = Product.create(emptyName, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNegativePrice_whenCallCreate_thenShouldThrownAnException() {
        // Arrange
        final var negativePrice = -100.0;
        final var expectedErrorMessage = "Price should be greater than zero";

        // Act
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, negativePrice);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNegativeQuantity_whenCallCreate_thenShouldThrownAnException() {
        // Arrange
        final var negativeQuantity = -10;
        final var expectedErrorMessage = "Quantity should be greater than zero";

        // Act
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, negativeQuantity, EXPECTED_PRICE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
