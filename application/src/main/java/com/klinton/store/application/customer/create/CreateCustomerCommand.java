package com.klinton.store.application.customer.create;

public record CreateCustomerCommand(
        String name,
        String email,
        String password,
        String phone
) {
}
