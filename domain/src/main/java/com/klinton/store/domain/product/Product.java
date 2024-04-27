package com.klinton.store.domain.product;

import com.klinton.store.domain.Entity;

import java.time.Instant;

public class Product extends Entity<ProductID> {

    private String name;

    private String description;

    private int quantity;

    private double price;

    private final Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    protected Product(
            ProductID productID,
            String name,
            String description,
            int quantity,
            double price,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        super(productID);
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Product with(final String name, final String description, final int quantity, final double price) {
        final var id = ProductID.unique();
        final var now = Instant.now();
        return new Product(id, name, description, quantity, price, now, now, null);
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
