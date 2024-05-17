package com.klinton.store.application.product.retrieve.get;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;

import java.util.Objects;

public class DefaultGetProductByIdUseCase extends GetProductByIdUseCase {

    private final ProductGateway productGateway;

    public DefaultGetProductByIdUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public GetProductByIdOutput execute(String id) {
        final var productId = ProductID.from(id);
        final var product = productGateway.getById(productId)
                .orElseThrow(Utils.notFound(productId, Product.class));

        return GetProductByIdOutput.from(product);
    }
}
