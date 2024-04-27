package com.klinton.store.domain.product;

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
}
