package com.klinton.store.domain.aggregates.product;

import com.klinton.store.domain.core.product.ProductPurchase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductPurchaseTest {

    private static final String PRODUCT_ID = "product_id";
    private static final String PURCHASE_ID = "purchase_id";
    private static final int QUANTITY = 1;
    private static final double UNIT_PRICE = 10.0;

    @Test
    public void givenValidParamsWithId_whenCallProductPurchaseWithMethod_thenCreateProductPurchase() {
        // Act
        final var productPurchase = ProductPurchase.with(PRODUCT_ID, PURCHASE_ID, QUANTITY, UNIT_PRICE);

        // Then
        assertNotNull(productPurchase);
        assertNotNull(productPurchase.id());
        assertEquals(PRODUCT_ID, productPurchase.productId());
        assertEquals(PURCHASE_ID, productPurchase.purchaseId());
        assertEquals(QUANTITY, productPurchase.quantity());
        assertEquals(UNIT_PRICE, productPurchase.unitPrice());
    }

    @Test
    public void givenValidParamsWithNoId_whenCallProductPurchaseWithMethod_thenCreateProductPurchase() {
        // Arrange
        final var id = "product_purchase_id";

        // Act
        final var productPurchase = ProductPurchase.with(id, PRODUCT_ID, PURCHASE_ID, QUANTITY, UNIT_PRICE);

        // Then
        assertNotNull(productPurchase);
        assertEquals(id, productPurchase.id());
        assertEquals(PRODUCT_ID, productPurchase.productId());
        assertEquals(PURCHASE_ID, productPurchase.purchaseId());
        assertEquals(QUANTITY, productPurchase.quantity());
        assertEquals(UNIT_PRICE, productPurchase.unitPrice());
    }
}
