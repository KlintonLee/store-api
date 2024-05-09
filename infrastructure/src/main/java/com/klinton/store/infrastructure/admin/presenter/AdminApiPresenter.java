package com.klinton.store.infrastructure.admin.presenter;

import com.klinton.store.application.admin.retrieve.get.GetAdminByIdOutput;

public interface AdminApiPresenter {

    static GetAdminOutput present(final GetAdminByIdOutput output) {
        return new GetAdminOutput(
                output.id(),
                output.name(),
                output.email(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
