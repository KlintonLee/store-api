package com.klinton.store.application.cart.update;

import com.klinton.store.domain.core.cart.Cart;
import com.klinton.store.domain.core.cart.CartGateway;

import java.util.Objects;

import static com.klinton.store.application.Utils.notFound;

public class DefaultUpdateCartUseCase extends UpdateCartUseCase {

    private final CartGateway cartGateway;

    public DefaultUpdateCartUseCase(final CartGateway cartGateway) {
        this.cartGateway = Objects.requireNonNull(cartGateway);
    }

    @Override
    public UpdateCartOutput execute(UpdateCartCommand command) {
        final var cart = cartGateway.findById(command.id())
                .orElseThrow(notFound(command.id(), Cart.class));

        cart.update(command.quantity(), command.price());
        return UpdateCartOutput.from(cartGateway.save(cart));
    }
}
