package com.klinton.store.application.cart.delete;

import com.klinton.store.domain.core.cart.CartGateway;

import java.util.Objects;

public class DefaultDeleteCartUseCase extends DeleteCartUseCase {

    private final CartGateway cartGateway;

    public DefaultDeleteCartUseCase(final CartGateway cartGateway) {
        this.cartGateway = Objects.requireNonNull(cartGateway);
    }

    @Override
    public void execute(String id) {
        cartGateway.delete(id);
    }
}
