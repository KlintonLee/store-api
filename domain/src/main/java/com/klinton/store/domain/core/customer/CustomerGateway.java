package com.klinton.store.domain.core.customer;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CustomerGateway {

    Customer save(Customer customer);

    Optional<Customer> getById(CustomerID customerID);

    void delete(CustomerID customerID);

    Pagination<Customer> getAll(SearchQuery query);
}
