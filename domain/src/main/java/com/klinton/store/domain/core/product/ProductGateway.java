package com.klinton.store.domain.core.product;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface ProductGateway {

    Product save(Product product);

    Optional<Product> getById(ProductID productID);

    Pagination<Product> getAll(SearchQuery query);
}
