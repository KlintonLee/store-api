package com.klinton.store.infrastructure.customer.presenter;

import com.klinton.store.application.customer.retrieve.get.GetCustomerByIdOutput;

public interface CustomerApiPresenter {
    static GetCustomerResponse present(final GetCustomerByIdOutput output) {
        return new GetCustomerResponse(
                output.id(),
                output.name(),
                output.email(),
                output.phone(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
