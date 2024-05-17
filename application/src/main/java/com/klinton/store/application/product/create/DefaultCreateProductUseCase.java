package com.klinton.store.application.product.create;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;

public class DefaultCreateProductUseCase extends CreateProductUseCase {

    private final ProductGateway productGateway;

    public DefaultCreateProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public CreateProductOutput execute(CreateProductCommand command) {
        final var product = Product.create(command.name(), command.description(), command.quantity(), command.price(), true);

        final var notification = Notification.create();
        product.validate(notification);

        if (notification.hasError()) {
            final var errorMessage = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(errorMessage);
        }

        return CreateProductOutput.from(productGateway.save(product));
    }
}
