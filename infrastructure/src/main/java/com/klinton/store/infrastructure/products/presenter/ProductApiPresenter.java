package com.klinton.store.infrastructure.products.presenter;

import com.klinton.store.application.product.retrieve.get.GetProductByIdOutput;

public interface ProductApiPresenter {

    static GetProductResponse present(final GetProductByIdOutput output) {
        return new GetProductResponse(
                output.id(),
                output.name(),
                output.description(),
                output.quantity(),
                output.price(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
