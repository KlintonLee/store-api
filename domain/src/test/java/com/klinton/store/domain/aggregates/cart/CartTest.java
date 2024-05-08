package com.klinton.store.domain.aggregates.cart;

import com.klinton.store.domain.aggregate.cart.Cart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartTest {

    private static final String CUSTOMER_ID = "customerId";

    private static final String PRODUCT_ID = "productId";

    private static final int QUANTITY = 1;

    private static final double PRICE = 100.00;

    @Test
    public void givenValidParams_whenCallCartCreate_thenShouldReturnANewOne() {
        // Act
        final var cart = Cart.with(CUSTOMER_ID, PRODUCT_ID, QUANTITY, PRICE);

        // Assert
        assertNotNull(cart);
        assertNotNull(cart.id());
        assertEquals(CUSTOMER_ID, cart.customerId());
        assertEquals(PRODUCT_ID, cart.productId());
        assertEquals(QUANTITY, cart.quantity());
        assertEquals(PRICE, cart.price());
    }

}
