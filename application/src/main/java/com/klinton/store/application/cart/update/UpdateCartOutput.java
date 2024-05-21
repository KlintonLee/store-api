package com.klinton.store.application.cart.update;

import com.klinton.store.domain.core.cart.Cart;

public record UpdateCartOutput(
        String id
) {
    public static UpdateCartOutput from(final String id) {
        return new UpdateCartOutput(id);
    }

    public static UpdateCartOutput from(final Cart cart) {
        return new UpdateCartOutput(cart.id());
    }
}
