package com.klinton.store.domain.core.address;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface AddressGateway {

    Address save(Address address);

    Optional<Address> getById(AddressId addressID);

    void delete(AddressId addressID);

    Pagination<Address> getAll(SearchQuery query);
}
