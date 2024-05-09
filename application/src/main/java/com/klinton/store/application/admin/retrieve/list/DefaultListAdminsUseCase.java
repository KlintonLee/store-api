package com.klinton.store.application.admin.retrieve.list;

import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListAdminsUseCase extends ListAdminsUseCase {

    private final AdminGateway adminGateway;

    public DefaultListAdminsUseCase(final AdminGateway adminGateway) {
        this.adminGateway = Objects.requireNonNull(adminGateway);
    }

    @Override
    public Pagination<ListAdminOutput> execute(SearchQuery query) {
        return adminGateway.getAll(query)
                .map(ListAdminOutput::from);
    }
}
