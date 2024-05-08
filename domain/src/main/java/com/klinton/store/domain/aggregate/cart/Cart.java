package com.klinton.store.domain.aggregate.cart;

import com.klinton.store.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class Cart extends ValueObject {

    private String id;

    private String customerId;

    private String productId;

    private int quantity;

    private double price;

    protected Cart(
            final String id,
            final String customerId,
            final String productId,
            final int quantity,
            final double price
    ) {
        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.productId = Objects.requireNonNull(productId);
        this.quantity = quantity;
        this.price = price;
    }

    public static Cart with(
            final String customerId,
            final String productId,
            final int quantity,
            final double price
    ) {
        final var id = UUID.randomUUID().toString();
        return new Cart(id, customerId, productId, quantity, price);
    }

    public static Cart with(
            final String id,
            final String customerId,
            final String productId,
            final int quantity,
            final double price
    ) {
        return new Cart(id, customerId, productId, quantity, price);
    }

    public String id() {
        return id;
    }

    public String customerId() {
        return customerId;
    }

    public String productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }

    public double price() {
        return price;
    }
}
