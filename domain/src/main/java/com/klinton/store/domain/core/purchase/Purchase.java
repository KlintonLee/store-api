package com.klinton.store.domain.core.purchase;

import com.klinton.store.domain.ValueObject;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Purchase extends ValueObject {

    private final String id;

    private final String customerId;

    private String AddressId;

    private Instant purchaseDate;

    private double totalPrice;

    private String paymentMethod;

    private PurchaseStatus status;

    private Instant updatedAt;

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

    public static Purchase with(
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

    public static Purchase with(
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

    public void update(
            final String AddressId,
            final double totalPrice,
            final String paymentMethod,
            final PurchaseStatus status
    ) {
        this.AddressId = Objects.requireNonNull(AddressId);
        this.totalPrice = totalPrice;
        this.paymentMethod = Objects.requireNonNull(paymentMethod);
        this.status = Objects.requireNonNull(status);
        this.updatedAt = Instant.now();
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
