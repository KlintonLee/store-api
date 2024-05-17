package com.klinton.store.application.product.retrieve.list;

import com.klinton.store.application.UseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

public abstract class ListProductsUseCase
        extends UseCase<SearchQuery, Pagination<ListProductOutput>> {
}
