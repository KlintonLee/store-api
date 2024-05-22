package com.klinton.store.application.cart.retrieve;

import com.klinton.store.domain.core.product.Product;

public record ListCartByCustomerOutput(
        String cartId,
        Product product,
        int quantity,
        double price
) {

    public static ListCartByCustomerOutput of(String cartId, Product product, int quantity, double price) {
        return new ListCartByCustomerOutput(cartId, product, quantity, price);
    }
}
