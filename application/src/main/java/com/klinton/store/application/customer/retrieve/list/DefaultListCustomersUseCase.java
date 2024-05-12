package com.klinton.store.application.customer.retrieve.list;

import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListCustomersUseCase extends ListCustomersUseCase {

    private final CustomerGateway customerGateway;

    public DefaultListCustomersUseCase(final CustomerGateway customerGateway) {
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public Pagination<ListCustomerOutput> execute(SearchQuery query) {
        return customerGateway.getAll(query)
                .map(ListCustomerOutput::from);
    }
}
