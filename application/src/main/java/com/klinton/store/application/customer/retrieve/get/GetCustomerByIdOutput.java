package com.klinton.store.application.customer.retrieve.get;

import com.klinton.store.domain.core.customer.Customer;

import java.time.Instant;

public record GetCustomerByIdOutput(
    String name,
    String email,
    String phone,
    Instant createdAt,
    Instant updatedAt,
    Instant deletedAt
) {

    public static GetCustomerByIdOutput from(Customer customer) {
        return new GetCustomerByIdOutput(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getDeletedAt()
        );
    }
}
