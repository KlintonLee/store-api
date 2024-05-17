package com.klinton.store.domain.core.product;

import com.klinton.store.domain.Entity;
import com.klinton.store.domain.validation.ValidationHandler;

import java.time.Instant;

public class Product extends Entity<ProductID> {

    private String name;

    private String description;

    private int quantity;

    private double price;

    private boolean active;

    private final Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    protected Product(
            ProductID productID,
            String name,
            String description,
            int quantity,
            double price,
            boolean active,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        super(productID);
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Product create(
            final String name,
            final String description,
            final int quantity,
            final double price,
            final boolean active
    ) {
        final var id = ProductID.unique();
        final var now = Instant.now();
        final var deletedAt = active ? null : now;

        return new Product(id, name, description, quantity, price, active, now, now, deletedAt);
    }

    public void update(
            final String name,
            final String description,
            final int quantity,
            final double price,
            final boolean active
    ) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.active = active;
        this.updatedAt = Instant.now();
        this.deletedAt = active ? null : Instant.now();
    }

    public void deactivate() {
        final var now = Instant.now();
        this.active = false;
        this.updatedAt = now;
        if (this.deletedAt == null) {
            this.deletedAt = now;
        }
    }

    public void activate() {
        this.active = true;
        this.updatedAt = Instant.now();
        this.deletedAt = null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }
}
