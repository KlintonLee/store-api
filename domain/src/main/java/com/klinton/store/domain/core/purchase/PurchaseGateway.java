package com.klinton.store.domain.core.purchase;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface PurchaseGateway {

    Purchase save(Purchase purchase);

    Optional<Purchase> getByID(PurchaseId purchaseID);

    void delete(PurchaseId purchaseID);

    Pagination<Purchase> getAll(SearchQuery query);
}
