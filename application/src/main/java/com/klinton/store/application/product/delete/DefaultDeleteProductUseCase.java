package com.klinton.store.application.product.delete;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;

import java.util.Objects;

public class DefaultDeleteProductUseCase extends DeleteProductUseCase {

    private final ProductGateway productGateway;

    public DefaultDeleteProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public void execute(String id) {
        final var productId = ProductID.from(id);
        final var product = productGateway.getById(productId)
                .orElseThrow(Utils.notFound(productId, Product.class));

        product.deactivate();
        productGateway.save(product);
    }
}
