package com.klinton.store.application.customer.retrieve.list;

import com.klinton.store.application.UseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

public abstract class ListCustomersUseCase
    extends UseCase<SearchQuery, Pagination<ListCustomerOutput>> {
}
