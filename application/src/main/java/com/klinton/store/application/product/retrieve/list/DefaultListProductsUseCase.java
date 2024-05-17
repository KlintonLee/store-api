package com.klinton.store.application.product.retrieve.list;

import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListProductsUseCase extends ListProductsUseCase {

    private final ProductGateway productGateway;

    public DefaultListProductsUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Pagination<ListProductOutput> execute(SearchQuery query) {
        return productGateway.getAll(query)
                .map(ListProductOutput::from);
    }
}
