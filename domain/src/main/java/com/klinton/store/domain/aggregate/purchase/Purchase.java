package com.klinton.store.domain.aggregate.purchase;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

import java.time.Instant;

public class Purchase extends AggregateRoot<PurchaseId> {

    private final String customerId;

    private final String AddressId;

    private final Instant purchaseDate;

    private final double totalPrice;

    private final String paymentMethod;

    protected Purchase(
            final PurchaseId purchaseId,
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod
    ) {
        super(purchaseId);
        this.customerId = customerId;
        this.AddressId = AddressId;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    public static Purchase create(
            final String customerId,
            final String AddressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod
    ) {
        var purchaseId = PurchaseId.unique();
        return new Purchase(purchaseId, customerId, AddressId, purchaseDate, totalPrice, paymentMethod);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddressId() {
        return AddressId;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
