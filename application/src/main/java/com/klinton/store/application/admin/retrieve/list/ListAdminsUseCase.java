package com.klinton.store.application.admin.retrieve.list;

import com.klinton.store.application.UseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

public abstract class ListAdminsUseCase
        extends UseCase<SearchQuery, Pagination<ListAdminOutput>> {
}
