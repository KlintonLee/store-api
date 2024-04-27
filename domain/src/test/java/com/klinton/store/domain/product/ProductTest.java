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
    public void givenValidParams_whenCallNewProduct_thenShouldReturnANewOne() {
        // Act
        final var product = Product.with(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);

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
    public void givenNullName_whenCallNewProduct_thenShouldThrownAnException() {
        // Arrange
        final String nullName = null;

        // Act
        final var product = Product.with(nullName, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals("Name should not be null or empty", exception.getMessage());
    }

    @Test
    public void givenEmptyName_whenCallNewProduct_thenShouldThrownAnException() {
        // Arrange
        final var emptyName = " ";

        // Act
        final var product = Product.with(emptyName, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals("Name should not be null or empty", exception.getMessage());
    }

    @Test
    public void givenNegativePrice_whenCallNewProduct_thenShouldThrownAnException() {
        // Arrange
        final var negativePrice = -100.0;

        // Act
        final var product = Product.with(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, negativePrice);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals("Price should be greater than zero", exception.getMessage());
    }

    @Test
    public void givenNegativeQuantity_whenCallNewProduct_thenShouldThrownAnException() {
        // Arrange
        final var negativeQuantity = -10;

        // Act
        final var product = Product.with(EXPECTED_NAME, EXPECTED_DESCRIPTION, negativeQuantity, EXPECTED_PRICE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals("Quantity should be greater than zero", exception.getMessage());
    }
}
