package com.klinton.store.application.address.retrieve.list;

import com.klinton.store.application.UseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

public abstract class ListAddressesUseCase
    extends UseCase<SearchQuery, Pagination<ListAddressOutput>> {
}
