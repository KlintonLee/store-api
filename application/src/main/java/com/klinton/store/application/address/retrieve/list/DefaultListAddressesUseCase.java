package com.klinton.store.application.address.retrieve.list;

import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListAddressesUseCase extends ListAddressesUseCase {

    private final AddressGateway addressGateway;

    public DefaultListAddressesUseCase(final AddressGateway addressGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public Pagination<ListAddressOutput> execute(SearchQuery input) {
        return addressGateway.getAll(input).map(ListAddressOutput::from);
    }
}
