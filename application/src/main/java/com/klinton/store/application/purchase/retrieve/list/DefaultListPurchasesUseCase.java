package com.klinton.store.application.purchase.retrieve.list;

import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListPurchasesUseCase extends ListPurchasesUseCase {

    private final PurchaseGateway purchaseGateway;

    public DefaultListPurchasesUseCase(final PurchaseGateway purchaseGateway) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
    }

    @Override
    public Pagination<ListPurchaseOutput> execute(SearchQuery query) {
        return purchaseGateway.getAll(query)
                .map(ListPurchaseOutput::from);
    }
}
