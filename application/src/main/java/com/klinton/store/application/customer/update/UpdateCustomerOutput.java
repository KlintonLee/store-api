package com.klinton.store.application.customer.update;

import com.klinton.store.domain.core.customer.Customer;

public record UpdateCustomerOutput(
        String id
) {
    public static UpdateCustomerOutput from(final String id) {
        return new UpdateCustomerOutput(id);
    }

    public static UpdateCustomerOutput from(final Customer admin) {
        return new UpdateCustomerOutput(admin.getId().getValue());
    }
}
