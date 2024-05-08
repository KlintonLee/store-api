package com.klinton.store.domain.core.product;

import com.klinton.store.domain.ValueObject;

import java.util.UUID;

public class ProductPurchase extends ValueObject {

    private final String id;

    private final String productId;

    private final String purchaseId;

    private final int quantity;

    private final double unitPrice;

    protected ProductPurchase(
            final String id,
            final String productId,
            final String purchaseId,
            final int quantity,
            final double unitPrice
    ) {
        this.id = id;
        this.productId = productId;
        this.purchaseId = purchaseId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static ProductPurchase with(
            final String productId,
            final String purchaseId,
            final int quantity,
            final double unitPrice
    ) {
        return new ProductPurchase(
                UUID.randomUUID().toString(),
                productId,
                purchaseId,
                quantity,
                unitPrice
        );
    }

    public static ProductPurchase with(
            final String id,
            final String productId,
            final String purchaseId,
            final int quantity,
            final double unitPrice
    ) {
        return new ProductPurchase(
                id,
                productId,
                purchaseId,
                quantity,
                unitPrice
        );
    }

    public String id() {
        return id;
    }

    public String productId() {
        return productId;
    }

    public String purchaseId() {
        return purchaseId;
    }

    public int quantity() {
        return quantity;
    }

    public double unitPrice() {
        return unitPrice;
    }
}
