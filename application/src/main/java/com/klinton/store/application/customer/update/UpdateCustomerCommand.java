package com.klinton.store.application.customer.update;

public record UpdateCustomerCommand(
        String id,
        String name,
        String email,
        String password,
        String phone,
        boolean active
) {
    public static UpdateCustomerCommand with(
            String id,
            String name,
            String email,
            String password,
            String phone,
            boolean active
    ) {
        return new UpdateCustomerCommand(id, name, email, password, phone, active);
    }
}
