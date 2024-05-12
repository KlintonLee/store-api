package com.klinton.store.application.customer.retrieve.list;

import com.klinton.store.domain.core.customer.Customer;

public record ListCustomerOutput(
        String id,
        String name,
        String email,
        String phone
) {
    public static ListCustomerOutput from(Customer customer) {
        return new ListCustomerOutput(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
