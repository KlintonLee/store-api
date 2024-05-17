package com.klinton.store.application.product.update;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;

public class DefaultUpdateProductUseCase extends UpdateProductUseCase {

    private final ProductGateway productGateway;

    public DefaultUpdateProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public UpdateProductOutput execute(UpdateProductCommand command) {
        final var productId = ProductID.from(command.id());
        final var product = productGateway.getById(productId)
                .orElseThrow(Utils.notFound(productId, Product.class));

        final var name = command.name();
        final var description = command.description();
        final var quantity = command.quantity();
        final var price = command.price();
        final var active = command.active();

        product.update(name, description, quantity, price, active);

        final var notification = Notification.create();
        product.validate(notification);

        if (notification.hasError()) {
            final var errorMessage = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(errorMessage);
        }

        return UpdateProductOutput.from(productGateway.save(product));
    }
}
