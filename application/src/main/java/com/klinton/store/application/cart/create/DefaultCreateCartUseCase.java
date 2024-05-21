package com.klinton.store.application.cart.create;

import com.klinton.store.domain.core.cart.Cart;
import com.klinton.store.domain.core.cart.CartGateway;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;

import java.util.Objects;

import static com.klinton.store.application.Utils.notFound;

public class DefaultCreateCartUseCase extends CreateCartUseCase {

    private final CartGateway cartGateway;

    private final CustomerGateway customerGateway;

    private final ProductGateway productGateway;

    public DefaultCreateCartUseCase(
            final CartGateway cartGateway,
            final CustomerGateway customerGateway,
            final ProductGateway productGateway
    ) {
        this.cartGateway = Objects.requireNonNull(cartGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public CreateCartOutput execute(CreateCartCommand command) {
        final var customerId = CustomerID.from(command.customerId());
        customerGateway.getById(customerId)
                .orElseThrow(notFound(customerId, Customer.class));

        final var productId = ProductID.from(command.productId());
        productGateway.getById(productId)
                .orElseThrow(notFound(productId, Product.class));

        final var cart = Cart.with(
                command.customerId(),
                command.productId(),
                command.quantity(),
                command.price()
        );
        return CreateCartOutput.from(cartGateway.save(cart));
    }
}
