package com.klinton.store.infrastructure.purchase.persistence;

import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "Purchase")
@Table(name = "purchases")
public class PurchaseJpaEntity {

    @Id
    private String id;

    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    @Column(name = "address_id", nullable = false)
    private String addressId;

    @Column(name = "purchase_date", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant purchaseDate;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    public PurchaseJpaEntity() {}

    private PurchaseJpaEntity(
            final String id,
            final String customerId,
            final String addressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod,
            final PurchaseStatus status,
            final Instant updatedAt
    ) {
        this.id = id;
        this.customerId = customerId;
        this.addressId = addressId;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public static PurchaseJpaEntity from(
            final Purchase purchase
    ) {
        return new PurchaseJpaEntity(
                purchase.id(),
                purchase.customerId(),
                purchase.addressId(),
                purchase.purchaseDate(),
                purchase.totalPrice(),
                purchase.paymentMethod(),
                purchase.status(),
                purchase.updatedAt()
        );
    }

    public Purchase toAggregate() {
        return Purchase.with(
                getId(),
                getCustomerId(),
                getAddressId(),
                getPurchaseDate(),
                getTotalPrice(),
                getPaymentMethod(),
                getStatus(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddressId() {
        return addressId;
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

    public PurchaseStatus getStatus() {
        return status;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
