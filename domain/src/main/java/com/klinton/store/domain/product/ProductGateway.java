package com.klinton.store.domain.product;

import com.klinton.store.domain.pagination.Pagination;

import java.util.Optional;

public interface ProductGateway {

    Product create(Product product);

    Optional<Product> getById(ProductID productID);

    Product update(Product product);

    void delete(ProductID productID);

    Pagination<Product> getAll(int page, int perPage);
}