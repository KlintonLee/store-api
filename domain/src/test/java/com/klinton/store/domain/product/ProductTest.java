package com.klinton.store.domain.product;

import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void givenValidParams_whenCallNewProduct_thenShouldReturnANewOne() {
        // Arrange
        final var name = "Product Name";
        final var description = "Product Description";
        final var quantity = 10;
        final var price = 100.0;

        // Act
        final var product = Product.with(name, description, quantity, price);

        // Assert
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(quantity, product.getQuantity());
        assertEquals(price, product.getPrice());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNull(product.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallNewProduct_thenShouldThrownAnException() {
        // Arrange
        final String name = null;
        final var description = "Product Description";
        final var quantity = 10;
        final var price = 100.0;

        // Act
        final var product = Product.with(name, description, quantity, price);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals("Name should not be null or empty", exception.getMessage());
    }

    @Test
    public void givenEmptyName_whenCallNewProduct_thenShouldThrownAnException() {
        // Arrange
        final var name = " ";
        final var description = "Product Description";
        final var quantity = 10;
        final var price = 100.0;

        // Act
        final var product = Product.with(name, description, quantity, price);
        final var exception = assertThrows(DomainException.class, () -> product.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals("Name should not be null or empty", exception.getMessage());
    }
}
