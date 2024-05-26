package com.klinton.store.infrastructure.cart.persistence;

import com.klinton.store.domain.core.cart.Cart;
import jakarta.persistence.*;

@Entity(name = "Cart")
@Table(name = "carts")
public class CartJpaEntity {

    @Id
    private String id;

    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    public CartJpaEntity() {}

    private CartJpaEntity(
            final String id,
            final String customerId,
            final String productId,
            final int quantity,
            final double price
    ) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static CartJpaEntity from(
            final Cart cart
    ) {
        return new CartJpaEntity(
                cart.id(),
                cart.customerId(),
                cart.productId(),
                cart.quantity(),
                cart.price()
        );
    }

    public Cart toAggregate() {
        return Cart.with(
                getId(),
                getCustomerId(),
                getProductId(),
                getQuantity(),
                getPrice()
        );
    }

    public String getId() {
        return id;
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
