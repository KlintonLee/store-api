package com.klinton.store.application.purchase.retrieve.list;

import com.klinton.store.application.UseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

public abstract class ListPurchasesUseCase
        extends UseCase<SearchQuery, Pagination<ListPurchaseOutput>> {
}
