package com.klinton.store.domain.core.purchase;

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

    private final PurchaseStatus status;

    private final Instant updatedAt;

    protected Purchase(
            final String id,
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod,
            final PurchaseStatus status,
            final Instant updatedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.AddressId = Objects.requireNonNull(AddressId);
        this.purchaseDate = Objects.requireNonNull(purchaseDate);
        this.totalPrice = totalPrice;
        this.paymentMethod = Objects.requireNonNull(paymentMethod);
        this.status = Objects.requireNonNull(status);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Purchase create(
            final String customerId,
            final String AddressId,
            final double totalPrice,
            final String paymentMethod,
            final PurchaseStatus status
    ) {
        final var id = UUID.randomUUID().toString();
        final var now = Instant.now();
        return new Purchase(id, customerId, AddressId, now, totalPrice, paymentMethod, status, now);
    }

    public static Purchase create(
            final String id,
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod,
            final PurchaseStatus status,
            final Instant updatedAt
    ) {
        return new Purchase(id, customerId, AddressId, purchaseDate, totalPrice, paymentMethod, status, updatedAt);
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

    public PurchaseStatus status() {
        return status;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
