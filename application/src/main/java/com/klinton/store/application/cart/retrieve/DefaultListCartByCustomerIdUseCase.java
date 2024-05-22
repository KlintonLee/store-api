package com.klinton.store.application.cart.retrieve;

import com.klinton.store.domain.core.cart.CartGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;

import java.util.List;
import java.util.Objects;

public class DefaultListCartByCustomerIdUseCase extends ListCartByCustomerIdUseCase {

    private final CartGateway cartGateway;

    private final ProductGateway productGateway;

    public DefaultListCartByCustomerIdUseCase(final CartGateway cartGateway, final ProductGateway productGateway) {
        this.cartGateway = Objects.requireNonNull(cartGateway);
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public List<ListCartByCustomerOutput> execute(String customerIdAsString) {
        final var customerId = CustomerID.from(customerIdAsString);
        final var carts = cartGateway.findAllByCustomerId(customerId);
        return carts.stream()
                .map(cart -> {
                    final var productId = ProductID.from(cart.productId());
                    return ListCartByCustomerOutput.of(
                            cart.id(),
                            productGateway.getById(productId).orElse(null),
                            cart.quantity(),
                            cart.price()
                    );
                })
                .toList();
    }
}
