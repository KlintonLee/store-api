package com.klinton.store.application.customer.retrieve.get;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.customer.Customer;

import java.time.Instant;
import java.util.List;

public record GetCustomerByIdOutput(
    String id,
    String name,
    String email,
    String phone,
    boolean active,
    List<Address> addresses,
    Instant createdAt,
    Instant updatedAt,
    Instant deletedAt
) {

    public static GetCustomerByIdOutput from(Customer customer) {
        return new GetCustomerByIdOutput(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.isActive(),
                customer.getAddresses(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getDeletedAt()
        );
    }
}
