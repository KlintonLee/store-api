package com.klinton.store.domain.aggregate.cart;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

import java.util.Objects;

public class Cart extends AggregateRoot<CartId> {

    private String customerId;

    private String productId;

    private int quantity;

    private double price;

    protected Cart(
            final CartId cartId,
            final String customerId,
            final String productId,
            final int quantity,
            final double price
    ) {
        super(cartId);
        this.customerId = Objects.requireNonNull(customerId);
        this.productId = Objects.requireNonNull(productId);
        this.quantity = quantity;
        this.price = price;
    }

    public static Cart create(
            final String customerId,
            final String productId,
            final int quantity,
            final double price
    ) {
        var cartId = CartId.unique();
        return new Cart(cartId, customerId, productId, quantity, price);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
