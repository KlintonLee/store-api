package com.klinton.store.application.cart.create;

import com.klinton.store.domain.core.cart.Cart;

public record CreateCartOutput(
        String id
) {

    public static CreateCartOutput from(String id) {
        return new CreateCartOutput(id);
    }

    public static CreateCartOutput from(Cart cart) {
        return new CreateCartOutput(cart.id());
    }
}
