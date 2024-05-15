package com.klinton.store.infrastructure.customer.presenter;

import com.klinton.store.application.customer.retrieve.get.GetCustomerByIdOutput;
import com.klinton.store.domain.core.address.Address;

public interface CustomerApiPresenter {
    static GetCustomerResponse present(final GetCustomerByIdOutput output) {
        return new GetCustomerResponse(
                output.id(),
                output.name(),
                output.email(),
                output.phone(),
                output.active(),
                output.addresses().stream().map(CustomerApiPresenter::mountAddressResponse).toList(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }


    private static CustomerAddressListResponse mountAddressResponse(Address address) {
        return new CustomerAddressListResponse(
                address.getId().getValue(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
