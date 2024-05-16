package com.klinton.store.domain.aggregates.purchase;

import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PurchaseTest {

    private static final String CUSTOMER_ID = "customer_id";

    private static final String ADDRESS_ID = "address_id";

    private static final double TOTAL = 100.0;

    private static final String PAYMENT_METHOD = "boleto";

    private static final PurchaseStatus STATUS = PurchaseStatus.COMPLETED;


    @Test
    public void givenValidParams_whenCallPurchaseCreate_thenShouldReturnANewOne() {
        // Act
        final var purchase = Purchase.create(CUSTOMER_ID, ADDRESS_ID, TOTAL, PAYMENT_METHOD, STATUS);

        // Assert
        assertNotNull(purchase);
        assertNotNull(purchase.id());
        assertEquals(CUSTOMER_ID, purchase.customerId());
        assertEquals(ADDRESS_ID, purchase.addressId());
        assertNotNull(purchase.purchaseDate());
        assertEquals(TOTAL, purchase.totalPrice());
        assertEquals(PAYMENT_METHOD, purchase.paymentMethod());
        assertNotNull(purchase.updatedAt());
    }
}
