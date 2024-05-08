package com.klinton.store.domain.aggregates.purchase;

import com.klinton.store.domain.aggregate.purchase.Purchase;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PurchaseTest {

    private static final String CUSTOMER_ID = "customer_id";

    private static final String ADDRESS_ID = "address_id";

    private static final Instant PURCHASE_DATE = Instant.now();

    private static final double TOTAL = 100.0;

    private static final String PAYMENT_METHOD = "boleto";


    @Test
    public void givenValidParams_whenCallPurchaseCreate_thenShouldReturnANewOne() {
        // Act
        final var purchase = Purchase.create(CUSTOMER_ID, ADDRESS_ID, PURCHASE_DATE, TOTAL, PAYMENT_METHOD);

        // Assert
        assertNotNull(purchase);
        assertNotNull(purchase.getId());
        assertEquals(CUSTOMER_ID, purchase.getCustomerId());
        assertEquals(ADDRESS_ID, purchase.getAddressId());
        assertEquals(PURCHASE_DATE, purchase.getPurchaseDate());
        assertEquals(TOTAL, purchase.getTotalPrice());
        assertEquals(PAYMENT_METHOD, purchase.getPaymentMethod());
    }
}
