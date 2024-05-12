package com.klinton.store.application.customer.create;

public record CreateCustomerCommand(
        String name,
        String email,
        String password,
        String phone
) {
    public static CreateCustomerCommand with(
            String name,
            String email,
            String password,
            String phone
    ) {
        return new CreateCustomerCommand(name, email, password, phone);
    }
}
