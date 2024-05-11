package com.klinton.store.application.customer.create;

import com.klinton.store.domain.core.customer.Customer;

public record CreateCustomerOutput(
        String id
) {
    public static CreateCustomerOutput from(final String id) {
        return new CreateCustomerOutput(id);
    }

    public static CreateCustomerOutput from(final Customer admin) {
        return new CreateCustomerOutput(admin.getId().getValue());
    }
}
