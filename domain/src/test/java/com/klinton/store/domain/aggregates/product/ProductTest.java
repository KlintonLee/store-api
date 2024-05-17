package com.klinton.store.domain.aggregates.product;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private final static String EXPECTED_NAME = "Product Name";

    private final static String EXPECTED_DESCRIPTION = "Product Description";

    private final static int EXPECTED_QUANTITY = 10;

    private final static double EXPECTED_PRICE = 100.0;

    private final static boolean EXPECTED_ACTIVE = true;

    @Test
    public void givenValidParams_whenCallCreate_thenShouldReturnANewOne() {
        // Act
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, EXPECTED_ACTIVE);

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(EXPECTED_NAME, product.getName());
        assertEquals(EXPECTED_DESCRIPTION, product.getDescription());
        assertEquals(EXPECTED_QUANTITY, product.getQuantity());
        assertEquals(EXPECTED_PRICE, product.getPrice());
        assertEquals(EXPECTED_ACTIVE, product.isActive());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNull(product.getDeletedAt());
    }

    @Test
    public void givenAnInactiveProduct_whenCallCreate_thenShouldReturnInactiveProduct() {
        // Arrange
        final var expectedActive = false;

        // Act
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, expectedActive);

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(EXPECTED_NAME, product.getName());
        assertEquals(EXPECTED_DESCRIPTION, product.getDescription());
        assertEquals(EXPECTED_QUANTITY, product.getQuantity());
        assertEquals(EXPECTED_PRICE, product.getPrice());
        assertEquals(expectedActive, product.isActive());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNotNull(product.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallCreate_thenShouldThrownAnException() {
        // Arrange
        final String nullName = null;
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var product = Product.create(nullName, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, EXPECTED_ACTIVE);
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
        final var product = Product.create(emptyName, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, EXPECTED_ACTIVE);
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
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, negativePrice, EXPECTED_ACTIVE);
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
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, negativeQuantity, EXPECTED_PRICE, EXPECTED_ACTIVE);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenValidParams_whenCallUpdate_thenShouldReturnUpdatedProduct() throws InterruptedException {
        // Arrange
        final var expectedName = "New Product Name";
        final var expectedDescription = "New Product Description";
        final var expectedQuantity = 20;
        final var expectedPrice = 200.0;
        final var expectedActive = false;
        final var product = Product.create(expectedName, expectedDescription, expectedQuantity, expectedPrice, expectedActive);
        final var createdAt = product.getCreatedAt();
        final var updatedAt = product.getUpdatedAt();

        // Act
        Thread.sleep(1);
        product.update(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, EXPECTED_ACTIVE);

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(EXPECTED_NAME, product.getName());
        assertEquals(EXPECTED_DESCRIPTION, product.getDescription());
        assertEquals(EXPECTED_QUANTITY, product.getQuantity());
        assertEquals(EXPECTED_PRICE, product.getPrice());
        assertEquals(EXPECTED_ACTIVE, product.isActive());
        assertEquals(createdAt, product.getCreatedAt());
        assertTrue(product.getUpdatedAt().isAfter(updatedAt));
        assertNull(product.getDeletedAt());
    }

    @Test
    public void givenAValidProduct_whenCallDeactivate_thenShouldReturnInactiveProduct() throws InterruptedException {
        // Arrange
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, EXPECTED_ACTIVE);
        final var createdAt = product.getCreatedAt();
        final var updatedAt = product.getUpdatedAt();

        // Act
        Thread.sleep(1);
        product.deactivate();

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(EXPECTED_NAME, product.getName());
        assertEquals(EXPECTED_DESCRIPTION, product.getDescription());
        assertEquals(EXPECTED_QUANTITY, product.getQuantity());
        assertEquals(EXPECTED_PRICE, product.getPrice());
        assertFalse(product.isActive());
        assertEquals(createdAt, product.getCreatedAt());
        assertTrue(product.getUpdatedAt().isAfter(updatedAt));
        assertNotNull(product.getDeletedAt());
    }

    @Test
    public void givenAnInactiveProduct_whenCallActivate_thenShouldReturnActiveProduct() throws InterruptedException {
        // Arrange
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, false);
        final var createdAt = product.getCreatedAt();
        final var updatedAt = product.getUpdatedAt();

        // Act
        Thread.sleep(1);
        product.activate();

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(EXPECTED_NAME, product.getName());
        assertEquals(EXPECTED_DESCRIPTION, product.getDescription());
        assertEquals(EXPECTED_QUANTITY, product.getQuantity());
        assertEquals(EXPECTED_PRICE, product.getPrice());
        assertTrue(product.isActive());
        assertEquals(createdAt, product.getCreatedAt());
        assertTrue(product.getUpdatedAt().isAfter(updatedAt));
        assertNull(product.getDeletedAt());
    }
}
