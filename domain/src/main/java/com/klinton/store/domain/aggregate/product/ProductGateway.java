package com.klinton.store.domain.aggregate.product;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface ProductGateway {

    Product create(Product product);

    Optional<Product> getById(ProductID productID);

    Product update(Product product);

    void delete(ProductID productID);

    Pagination<Product> getAll(SearchQuery query);
}
