package com.klinton.store.domain.aggregate.purchase;

import com.klinton.store.domain.ValueObject;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Purchase extends ValueObject {

    private final String id;

    private final String customerId;

    private final String AddressId;

    private final Instant purchaseDate;

    private final double totalPrice;

    private final String paymentMethod;

    protected Purchase(
            final String id,
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod
    ) {
        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.AddressId = Objects.requireNonNull(AddressId);
        this.purchaseDate = Objects.requireNonNull(purchaseDate);
        this.totalPrice = totalPrice;
        this.paymentMethod = Objects.requireNonNull(paymentMethod);
    }

    public static Purchase with(
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod
    ) {
        final var id = UUID.randomUUID().toString();
        return new Purchase(id, customerId, AddressId, purchaseDate, totalPrice, paymentMethod);
    }

    public static Purchase with(
            final String id,
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod
    ) {
        return new Purchase(id, customerId, AddressId, purchaseDate, totalPrice, paymentMethod);
    }

    public String id() {
        return id;
    }

    public String customerId() {
        return customerId;
    }

    public String addressId() {
        return AddressId;
    }

    public Instant purchaseDate() {
        return purchaseDate;
    }

    public double totalPrice() {
        return totalPrice;
    }

    public String paymentMethod() {
        return paymentMethod;
    }
}
